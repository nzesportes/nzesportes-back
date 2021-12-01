package br.com.nzesportes.api.nzapi.services.payment;

import br.com.nzesportes.api.nzapi.domains.customer.Customer;
import br.com.nzesportes.api.nzapi.domains.customer.User;
import br.com.nzesportes.api.nzapi.domains.product.ProductDetails;
import br.com.nzesportes.api.nzapi.domains.product.Sale;
import br.com.nzesportes.api.nzapi.domains.product.Stock;
import br.com.nzesportes.api.nzapi.domains.purchase.PaymentRequest;
import br.com.nzesportes.api.nzapi.domains.purchase.Purchase;
import br.com.nzesportes.api.nzapi.domains.purchase.PurchaseItems;
import br.com.nzesportes.api.nzapi.domains.purchase.MercadoPagoPaymentStatus;
import br.com.nzesportes.api.nzapi.dtos.enums.EmailContentEnum;
import br.com.nzesportes.api.nzapi.domains.product.Coupon;
import br.com.nzesportes.api.nzapi.domains.purchase.*;
import br.com.nzesportes.api.nzapi.dtos.mercadopago.order.OrderPage;
import br.com.nzesportes.api.nzapi.dtos.mercadopago.order.OrderPaymentStatus;
import br.com.nzesportes.api.nzapi.dtos.mercadopago.order.OrderStatus;
import br.com.nzesportes.api.nzapi.dtos.mercadopago.order.OrderTO;
import br.com.nzesportes.api.nzapi.dtos.mercadopago.payment.PaymentMPTO;
import br.com.nzesportes.api.nzapi.dtos.mercadopago.preference.*;
import br.com.nzesportes.api.nzapi.dtos.product.UpdateStockTO;
import br.com.nzesportes.api.nzapi.dtos.purchase.PaymentPurchaseTO;
import br.com.nzesportes.api.nzapi.dtos.purchase.PaymentTO;
import br.com.nzesportes.api.nzapi.dtos.purchase.ProductPaymentTO;
import br.com.nzesportes.api.nzapi.errors.ResourceConflictException;
import br.com.nzesportes.api.nzapi.errors.ResponseErrorEnum;
import br.com.nzesportes.api.nzapi.feign.MercadoPagoClient;
import br.com.nzesportes.api.nzapi.repositories.purchase.PurchaseCodeRepository;
import br.com.nzesportes.api.nzapi.repositories.purchase.PurchaseRepository;
import br.com.nzesportes.api.nzapi.security.services.UserDetailsImpl;
import br.com.nzesportes.api.nzapi.services.customer.AddressService;
import br.com.nzesportes.api.nzapi.services.customer.CustomerService;
import br.com.nzesportes.api.nzapi.services.customer.UserService;
import br.com.nzesportes.api.nzapi.services.email.EmailService;
import br.com.nzesportes.api.nzapi.services.product.CouponService;
import br.com.nzesportes.api.nzapi.services.product.ProductService;
import br.com.nzesportes.api.nzapi.services.product.StockService;
import br.com.nzesportes.api.nzapi.utils.ProductUtils;
import br.com.nzesportes.api.nzapi.utils.PurchaseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@EnableScheduling
@Transactional
public class PaymentService {
    @Value("${nz.webhook}")
    private String WEBHOOK_URL;

    @Value("${nz.mercado-pago.token}")
    private String TOKEN;

    @Value("${nz.mercado-pago.payment.back-url}")
    private String PAYMENT_BACK_URL;

    @Value("${nz.mercado-pago.auto-return}")
    private String AUTO_RETURN;

    @Value("${nz.front.url}")
    private String urlFront;

    private final static String CURRENCY = "BRL";
    private final static String IDENTIFICATION_TYPE = "CPF";
    private final static String STATEMENT = "NZESPORTES";

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private StockService stockService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private PurchaseCodeRepository purchaseCodeRepository;

    @Autowired
    private AddressService addressService;

    @Autowired
    private MercadoPagoClient mercadoPagoAPI;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private ProductUtils utils;

    public PaymentPurchaseTO createPaymentRequest(PaymentTO dto, UserDetailsImpl principal) {
        return createPurchase(dto, principal);
    }

    public Page<Purchase> getAllByCustomerId(UUID customerId, int page, int size) {
        return purchaseRepository.findAllByCustomerId(customerId, PageRequest.of(page, size));
    }

    public Page<Purchase> getAll(BigInteger code, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if(Objects.nonNull(code))
            return purchaseRepository.findByCode(code, pageable);
        return purchaseRepository.findAllPurchase(pageable);
    }

    public Purchase getById(UUID id) {
        return purchaseRepository.findAllById(id);
    }

    public void checkPaymentStatus(Purchase purchase) {
        PaymentMPTO payment = mercadoPagoAPI.getPayment("Bearer " + TOKEN, purchase.getPaymentRequest().getPaymentId());
        if(MercadoPagoPaymentStatus.cancelled.equals(payment.getStatus()))
            cancelPurchase(purchase);
        else {
            if(purchase.getTicket() == null || purchase.getTicket().equals(Boolean.FALSE)) {
                purchase.setTicket(payment.getPayment_type_id().equals("ticket"));
                purchase.getPaymentRequest().setExpirationDate(payment.getDate_of_expiration());
            }
            updateStatus(purchase, payment);
        }
    }

    private PaymentPurchaseTO createPurchase(PaymentTO dto, UserDetailsImpl principal) {
        Customer customer = customerService.getByUserId(principal.getId());
        Purchase purchase = Purchase.builder()
                .customer(customer)
                .shipment(dto.getShipment())
                .shipmentServiceId(dto.getShipmentService())
                .totalCost(dto.getShipment())
                .status(MercadoPagoPaymentStatus.pending)
                .paymentRequest(PaymentRequest.builder().creationDate(LocalDateTime.now()).buyerId(customer.getId()).build())
                .shipmentAddress(addressService.getById(dto.getShipmentId()))
                .build();

        List<PurchaseItems> items = new ArrayList<>();
        dto.getProducts().forEach(productPaymentTO -> {
            Stock updatedStock;
            try {
                updatedStock = stockService.updateQuantity(new UpdateStockTO(productPaymentTO.getStockId(), -productPaymentTO.getQuantity()));

                Boolean available = (updatedStock != null && updatedStock.getProductDetail().getStatus());
                if(!available)
                    cancelPurchaseProcess(items);

                PurchaseItems pi = PurchaseItems
                        .builder()
                        .item(updatedStock)
                        .available(available)
                        .quantity(productPaymentTO.getQuantity()).build();

                purchase.setTotalCost(purchase.getTotalCost().add(calculateDiscount(updatedStock, pi, productPaymentTO)));
                items.add(pi);
            }
            catch (Exception e) {
                log.error("Exception finding stock");
                cancelPurchaseProcess(items);
                throw new ResourceConflictException(ResponseErrorEnum.PAY001);

            }
        });
        purchase.setItems(items);

        if(purchase.getTotalCost().equals(purchase.getShipment()))
            throw new ResourceConflictException(ResponseErrorEnum.PAY001);

        if(dto.getCoupon() != null && !dto.getCoupon().isBlank()){
            if(!couponService.getStatus(dto.getCoupon()).getStatus())
                throw new ResourceConflictException(ResponseErrorEnum.NOT_FOUND);

            Coupon coupon = couponService.getByCode(dto.getCoupon());
            coupon.setQuantityLeft(coupon.getQuantityLeft() - 1);
            coupon = couponService.update(coupon);

            purchase.setTotalCost(purchase.getTotalCost().subtract(coupon.getDiscount()));
            purchase.setCoupon(coupon);
        }

        Purchase saved = purchaseRepository.save(purchase);
        PurchaseCode code = purchaseCodeRepository.save(PurchaseCode.builder().purchaseId(saved.getId()).build());
        Preference preference = createPreference(saved);

        saved.getPaymentRequest().setPreferenceId(preference.getId());
        saved.setCode(code.getId());
        saved = purchaseRepository.save(saved);
        sendEmailPurchase(saved, saved.getStatus());

        emailService.
            sendEmailPurchase(
                principal.getUsername(),
                    "NZESPORTES: PEDIDO CONFIRMADO " + saved.getCode(),
                    EmailContentEnum.COMPRA_CONFIRMADA.getText(),
                    "Obrigado pela compra!",
                    "ver pedido",
                    this.urlFront,
                    saved
            );

        return PaymentPurchaseTO.builder().purchaseId(saved.getId()).paymentUrl(preference.getInit_point()).build();
    }

    private Preference createPreference(Purchase purchase) {
        List<Item> items = new ArrayList<>();
        purchase.getItems().parallelStream()
                .forEach(purchaseItem -> items.add(Item.builder()
                        .title(productService.getById(purchaseItem.getItem().getProductDetail().getProductId()).getModel())
                        .id(purchaseItem.getItem().getId().toString())
                        .currency_id(CURRENCY)
                        .picture_url(Arrays.asList(purchaseItem.getItem().getProductDetail().getImages()).get(0))
                        .description(purchaseItem.getItem().getProductDetail().getDescription())
                        .quantity(purchaseItem.getQuantity())
                        .unit_price(purchaseItem.getPurchaseCost())
                        .build()));

        items.add(Item.builder().unit_price(purchase.getShipment()).quantity(1).description("Taxa de frete").title("Entrega").build());

        if(purchase.getCoupon() != null)
            items.add(Item.builder().unit_price(purchase.getCoupon().getDiscount().negate()).quantity(1).description("Cupom: " + purchase.getCoupon().getCode()).title("Desconto de cupom").build());

        Payer payer = Payer.builder()
                .name(purchase.getCustomer().getName())
                .surname(purchase.getCustomer().getLastName())
                .email(userService.getById(purchase.getCustomer().getUserId()).getUsername())
                .phone(Phone.builder().area_code(purchase.getCustomer().getPhone().substring(0, 2)).number(purchase.getCustomer().getPhone().substring(2)).build())
                .identification(Identification.builder().type(IDENTIFICATION_TYPE).number(purchase.getCustomer().getCpf()).build())
                .address(Address.builder().zip_code(purchase.getShipmentAddress().getCep()).street_name(purchase.getShipmentAddress().getStreet()).street_number(Integer.parseInt(purchase.getShipmentAddress().getNumber())).build())
                .build();

        List<ExcludedPaymentMethod> excludedPaymentMethods = new ArrayList<>();
        excludedPaymentMethods.add(ExcludedPaymentMethod.builder().id("pec").build());

        Preference preference = Preference.builder()
                .payer(payer)
                .items(items)
                .notification_url(WEBHOOK_URL + purchase.getId().toString())
                .expires(true)
                .expiration_date_from(OffsetDateTime.now())
                .expiration_date_to(OffsetDateTime.now().plusMinutes(30))
                .statement_descriptor(STATEMENT)
                .external_reference(purchase.getId().toString())
                .payment_methods(PaymentMethods.builder().excluded_payment_methods(excludedPaymentMethods).build())
                .back_urls(BackUrls.builder().success(PAYMENT_BACK_URL).failure(PAYMENT_BACK_URL).pending(PAYMENT_BACK_URL).build())
                .auto_return(AUTO_RETURN)
                .build();

        log.info("Preference: {}", preference);

        Preference savedPreference = mercadoPagoAPI.createPreference("Bearer " + TOKEN, preference);
        return savedPreference;
    }

    private void cancelPurchase(Purchase purchase) {
        purchase.setStatus(MercadoPagoPaymentStatus.cancelled);
        purchase.getItems().forEach(purchaseItems -> stockService.updateQuantity(new UpdateStockTO(purchaseItems.getItem().getId(), purchaseItems.getQuantity())));
        Purchase saved = purchaseRepository.save(purchase);
        sendEmailPurchase(saved, saved.getStatus());
    }

    private void cancelPurchaseProcess(List<PurchaseItems> items) {
        items.forEach(purchaseItems -> stockService.updateQuantity(new UpdateStockTO(purchaseItems.getItem().getId(), purchaseItems.getQuantity())));;
        throw new ResourceConflictException(ResponseErrorEnum.PAY001);
    }

    private void updateStatus(Purchase purchase, PaymentMPTO payment) {
        purchase.setStatus(payment.getStatus());
        if(payment.getStatus().equals(MercadoPagoPaymentStatus.approved)){
            purchase.getPaymentRequest().setConfirmationDate(LocalDateTime.now());
        }
        else if (MercadoPagoPaymentStatus.cancelled.equals(payment.getStatus())){
            purchase.getPaymentRequest().setCancellationDate(LocalDateTime.now());
        }

        Purchase saved = purchaseRepository.save(purchase);
        sendEmailPurchase(saved, saved.getStatus());
    }

    @Scheduled(cron = "* 30 * * * *")
    public void checkPayments() {
        log.info("Checking pending payments...");
        List<Purchase> purchases = purchaseRepository.findByStatus(MercadoPagoPaymentStatus.pending);
        purchases.addAll(purchaseRepository.findByStatus(MercadoPagoPaymentStatus.rejected));

        purchases.parallelStream().forEach(purchase -> {
            try {
                log.info("Getting preferences for purchase {}...", purchase.toString());
                PreferenceSearchPage preferences = mercadoPagoAPI.getPreferences("Bearer " + TOKEN, purchase.getId().toString());
                log.info("Preferences from mercado pago: {}",  preferences.getElements().toString());

                Preference preference = mercadoPagoAPI.getPreferenceById("Bearer " + TOKEN, preferences.getElements().get(0).getId());
                if(preference.getExpiration_date_to().isBefore(OffsetDateTime.now())) {
                    OrderPage orders = mercadoPagoAPI.getOrders("Bearer " + TOKEN, purchase.getId().toString(), null);
                    log.info("Orders from mercado pago: {}",  orders.getElements());

                    if((orders.getElements() == null || orders.getElements().size() == 0) || filterOrders(orders).size() == 0) {
                        log.error("Canceling purchase: {}", purchase.toString());
                        cancelPurchase(purchase);
                        return;
                    }
                }
            } catch (Exception e) {
                log.error("Exception while trying to check payment for purchase {}", purchase.toString());
                log.error("Exception while trying to check payment for purchase {}", e.toString());
                e.printStackTrace();
            }
        });
    }

    public Purchase tag(UUID id) {
        Purchase purchase = getById(id);
        purchase.setTag(!purchase.getTag());
        return purchaseRepository.save(purchase);
    }

    private List<OrderTO> filterOrders(OrderPage orders) {
        return orders.getElements().parallelStream().filter(order -> order.getOrder_status().equals(OrderPaymentStatus.paid)
                || order.getOrder_status().equals(OrderPaymentStatus.payment_in_process)
                || (order.getOrder_status().equals(OrderPaymentStatus.payment_required) && OrderStatus.opened.equals(order.getStatus()))
                || order.getOrder_status().equals(OrderPaymentStatus.partially_paid)).collect(Collectors.toList());
    }

    private BigDecimal calculateDiscount(Stock updatedStock, PurchaseItems pi, ProductPaymentTO productPaymentTO) {
        Optional<Sale> sale = utils.getSale(updatedStock.getProductDetail());
        BigDecimal result;
        if(sale.isPresent()) {
            result = new BigDecimal("100").subtract(new BigDecimal(sale.get().getPercentage().toString()))
                    .divide(new BigDecimal("100")).multiply(updatedStock.getProductDetail().getPrice())
                    .multiply(new BigDecimal(productPaymentTO.getQuantity().toString()));

            pi.setDiscount((sale.get().getPercentage()));
        }
        else
            result = updatedStock.getProductDetail().getPrice()
                    .multiply(new BigDecimal(productPaymentTO.getQuantity().toString()));

        pi.setCost(updatedStock.getProductDetail().getPrice());
        return result;
    }

    public void sendEmailPurchase(Purchase purchase, MercadoPagoPaymentStatus status) {
        User user = userService.getById(purchase.getCustomer().getUserId());
        String subject = "";
        String title = "";
        String text;

        switch (status) {
            case approved:
                subject = "NZESPORTES: PAGAMENTO APROVADO ";
                title = "Obrigado pela compra!";
                text = EmailContentEnum.COMPRA_APROVADA.getText();
                emailService.
                    sendEmailPurchase(
                            user.getUsername(),
                            "NZESPORTES: AVALIE SEU PRODUTO " + purchase.getCode(),
                            EmailContentEnum.COMPRA_AVALIAR.getText(),
                            "Olá, o que achou dos produtos?",
                            "avaliar produto",
                            this.urlFront + "avaliar/" + purchase.getId(),
                            purchase
                    );
                break;
            case cancelled:
                subject = "NZESPORTES: PAGAMENTO CANCELADO ";
                title = "Ops, sua compra foi cancelada!";
                text = EmailContentEnum.COMPRA_CANCELADA.getText();
                break;
            case rejected:
                subject = "NZESPORTES: PAGAMENTO NÃO APROVADO ";
                title = "Ops, seu pagamento não foi não foi aprovado!";
                text = EmailContentEnum.COMPRA_REJEITADA.getText();
                break;
            default:
                subject = "NZESPORTES: PROBLEMA NO PEDIDO ";
                title = "Ops, ocorreu um problema no seu pedido!";
                text = EmailContentEnum.COMPRA_ERRO_GENERICO.getText();

        }
        emailService.
            sendEmailPurchase(
                    user.getUsername(),
                    subject + purchase.getCode(),
                    text,
                    title,
                    "ver pedido",
                    this.urlFront,
                    purchase
            );
    }
}
