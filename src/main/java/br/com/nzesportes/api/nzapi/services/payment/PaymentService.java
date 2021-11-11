package br.com.nzesportes.api.nzapi.services.payment;

import br.com.nzesportes.api.nzapi.domains.customer.Customer;
import br.com.nzesportes.api.nzapi.domains.product.Stock;
import br.com.nzesportes.api.nzapi.domains.purchase.PaymentRequest;
import br.com.nzesportes.api.nzapi.domains.purchase.Purchase;
import br.com.nzesportes.api.nzapi.domains.purchase.PurchaseItems;
import br.com.nzesportes.api.nzapi.domains.purchase.MercadoPagoPaymentStatus;
import br.com.nzesportes.api.nzapi.dtos.mercadopago.order.OrderPage;
import br.com.nzesportes.api.nzapi.dtos.mercadopago.order.OrderPaymentStatus;
import br.com.nzesportes.api.nzapi.dtos.mercadopago.order.OrderStatus;
import br.com.nzesportes.api.nzapi.dtos.mercadopago.payment.PaymentMPTO;
import br.com.nzesportes.api.nzapi.dtos.mercadopago.preference.*;
import br.com.nzesportes.api.nzapi.dtos.product.UpdateStockTO;
import br.com.nzesportes.api.nzapi.dtos.purchase.PaymentPurchaseTO;
import br.com.nzesportes.api.nzapi.dtos.purchase.PaymentTO;
import br.com.nzesportes.api.nzapi.errors.ResourceConflictException;
import br.com.nzesportes.api.nzapi.errors.ResponseErrorEnum;
import br.com.nzesportes.api.nzapi.feign.MercadoPagoClient;
import br.com.nzesportes.api.nzapi.repositories.purchase.PurchaseRepository;
import br.com.nzesportes.api.nzapi.security.services.UserDetailsImpl;
import br.com.nzesportes.api.nzapi.services.customer.AddressService;
import br.com.nzesportes.api.nzapi.services.customer.CustomerService;
import br.com.nzesportes.api.nzapi.services.customer.UserService;
import br.com.nzesportes.api.nzapi.services.product.ProductService;
import br.com.nzesportes.api.nzapi.services.product.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    private final static String CURRENCY = "BRL";
    private final static String IDENTIFICATION_TYPE = "CPF";
    private final static String STATEMENT = "NZESPORTES";
    private final static String SORT_TYPE = "date_created";
    private final static String CRITERIA = "desc";


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
    private AddressService addressService;

    @Autowired
    private MercadoPagoClient mercadoPagoAPI;

    public PaymentPurchaseTO createPaymentRequest(PaymentTO dto, UserDetailsImpl principal) {
        return createPurchase(dto, principal);
    }

    public void checkPaymentStatus(Purchase purchase) {
        PaymentMPTO payment = mercadoPagoAPI.getPayment("Bearer " + TOKEN, purchase.getPaymentRequest().getPaymentId());
        if(MercadoPagoPaymentStatus.cancelled.equals(payment.getStatus()))
            cancelPurchase(purchase);
        else
            updateStatus(purchase, payment.getStatus());
    }

    private PaymentPurchaseTO createPurchase(PaymentTO dto, UserDetailsImpl principal) {
        Customer customer = customerService.getByUserId(principal.getId());
        Purchase purchase = Purchase.builder()
                .customer(customer)
                .shipment(dto.getShipment())
                .shipmentServiceId(dto.getShipmentService())
                .totalCost(dto.getShipment())
                .status(MercadoPagoPaymentStatus.pending)
                .paymentRequest(PaymentRequest.builder().buyerId(customer.getId()).build())
                .shipmentAddress(addressService.getById(dto.getShipmentId()))
                .build();

        List<PurchaseItems> items = new ArrayList<>();
        dto.getProducts().parallelStream().forEach(productPaymentTO -> {
            Stock updatedStock;
            try {
                updatedStock = stockService.updateQuantity(new UpdateStockTO(productPaymentTO.getStockId(), -productPaymentTO.getQuantity()));

                Boolean available = (updatedStock != null && updatedStock.getProductDetail().getStatus());

                PurchaseItems pi = PurchaseItems
                        .builder()
                        .item(updatedStock)
                        .available(available)
                        .quantity(productPaymentTO.getQuantity()).build();

                if(available) {
                    purchase.setTotalCost(purchase.getTotalCost().add(updatedStock.getProductDetail().getPrice()
                            .multiply(new BigDecimal(productPaymentTO.getQuantity().toString()))));
                    pi.setCost(updatedStock.getProductDetail().getPrice());
                }
                items.add(pi);
            }
            catch (Exception e) {
                log.error("Exception finding stock");
            }
        });
        purchase.setItems(items);

        if(purchase.getTotalCost().equals(purchase.getShipment()))
            throw new ResourceConflictException(ResponseErrorEnum.PAY001);
        Purchase saved = purchaseRepository.save(purchase);
        Preference preference = createPreference(saved);
        saved.getPaymentRequest().setPreferenceId(preference.getId());
        saved = purchaseRepository.save(saved);
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
                        .unit_price(purchaseItem.getCost())
                        .build()));

        items.add(Item.builder().unit_price(purchase.getShipment()).quantity(1).description("Taxa de frete").title("Entrega").build());

        Payer payer = Payer.builder()
                .name(purchase.getCustomer().getName())
                .surname(purchase.getCustomer().getLastName())
                .email(userService.getById(purchase.getCustomer().getUserId()).getUsername())
                .phone(Phone.builder().area_code(purchase.getCustomer().getPhone().substring(0, 2)).number(purchase.getCustomer().getPhone().substring(2)).build())
                .identification(Identification.builder().type(IDENTIFICATION_TYPE).number(purchase.getCustomer().getCpf()).build())
                .address(Address.builder().zip_code(purchase.getShipmentAddress().getCep()).street_name(purchase.getShipmentAddress().getStreet()).street_number(Integer.parseInt(purchase.getShipmentAddress().getNumber())).build())
                .build();

        Preference preference = Preference.builder()
                .payer(payer)
                .items(items)
                .notification_url(WEBHOOK_URL + purchase.getId().toString())
                .expires(true)
                .expiration_date_from(OffsetDateTime.now())
                .expiration_date_to(OffsetDateTime.now().plusMinutes(30))
                .statement_descriptor(STATEMENT)
                .external_reference(purchase.getId().toString())
                .back_urls(BackUrls.builder().success(PAYMENT_BACK_URL).failure(PAYMENT_BACK_URL).pending(PAYMENT_BACK_URL).build())
                .auto_return(AUTO_RETURN)
                .build();

        Preference savedPreference = mercadoPagoAPI.createPreference("Bearer " + TOKEN, preference);
        return savedPreference;
    }

    private void cancelPurchase(Purchase purchase) {
        purchase.setStatus(MercadoPagoPaymentStatus.cancelled);
        purchase.getItems().parallelStream().forEach(purchaseItems -> stockService.updateQuantity(new UpdateStockTO(purchaseItems.getItem().getId(), purchaseItems.getQuantity())));
        purchaseRepository.save(purchase);
    }

    private void updateStatus(Purchase purchase, MercadoPagoPaymentStatus status) {
        purchase.setStatus(status);
        if(status.equals(MercadoPagoPaymentStatus.approved)){
            purchase.getPaymentRequest().setConfirmationDate(LocalDateTime.now());
        }
        purchaseRepository.save(purchase);
    }

    @Scheduled(cron = "* 30 * * * *")
    public void checkPayments() {
        log.info("Checking pending payments...");
        List<Purchase> purchases = purchaseRepository.findByStatus(MercadoPagoPaymentStatus.pending);

        purchases.parallelStream().forEach(purchase -> {
            try {
                log.info("Getting preferences for purchase {}...", purchase.getId());
                PreferenceSearchPage preferences = mercadoPagoAPI.getPreferences("Bearer " + TOKEN, purchase.getId().toString());
                Preference preference = mercadoPagoAPI.getPreferenceById("Bearer " + TOKEN, preferences.getElements().get(0).getId());

                log.info("Getting closed orders...");
                OrderPage closedOrders = mercadoPagoAPI.getOrders("Bearer " + TOKEN, purchase.getId().toString(), OrderStatus.closed.getText());

                if(closedOrders.getElements() != null && closedOrders.getElements().size() > 0) {
                    log.info("Approving purchase with at least one order closed...");
                    closedOrders.getElements().parallelStream()
                            .forEach(orderTO -> {
                                if (OrderPaymentStatus.paid.equals(orderTO.getOrder_status()))
                                    updateStatus(purchase, MercadoPagoPaymentStatus.approved);
                                    return;});
                }
                else {
                    log.info("Searching for open and closed orders...");
                    OrderPage openOrders = mercadoPagoAPI.getOrders("Bearer " + TOKEN, purchase.getId().toString(), OrderStatus.opened.getText());
                    OrderPage expiredOrders = mercadoPagoAPI.getOrders("Bearer " + TOKEN, purchase.getId().toString(), OrderStatus.expired.getText());

                    if((openOrders.getElements() == null || closedOrders.getElements().size() == 0)
                            && preference.getExpiration_date_to().isAfter(OffsetDateTime.now())) {
                        log.info("Closing purchases with no payments...");
                        cancelPurchase(purchase);
                    }

                    else if(openOrders.getElements() != null && closedOrders.getElements().size() > 0) {
                        openOrders.getElements().forEach(orderTO -> {

                        });
                    }
                }
            } catch (Exception e) {
                log.error("Exception while trying to check payment for purchase {}", purchase.getId());
            }
        });
    }
}
