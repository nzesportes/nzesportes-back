package br.com.nzesportes.api.nzapi.services.payment;

import br.com.nzesportes.api.nzapi.domains.customer.Customer;
import br.com.nzesportes.api.nzapi.domains.product.Stock;
import br.com.nzesportes.api.nzapi.domains.purchase.PaymentRequest;
import br.com.nzesportes.api.nzapi.domains.purchase.Purchase;
import br.com.nzesportes.api.nzapi.domains.purchase.PurchaseItems;
import br.com.nzesportes.api.nzapi.domains.purchase.PurchaseStatus;
import br.com.nzesportes.api.nzapi.dtos.mercadopago.payment.MercadoPagoPaymentStatus;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@Transactional
public class PaymentService {
    @Value("${nz.webhook}")
    private String WEBHOOK_URL;

    @Value("${nz.mercado-pago.token}")
    private String TOKEN;

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
    private AddressService addressService;

    @Autowired
    private MercadoPagoClient mercadoPagoAPI;

    public PaymentPurchaseTO createPaymentRequest(PaymentTO dto, UserDetailsImpl principal) {
        return createPurchase(dto, principal);
    }

    public void checkPaymentStatus(Purchase purchase) {
        PaymentMPTO payment = mercadoPagoAPI.getPayment(purchase.getPaymentRequest().getPaymentId());
        if(MercadoPagoPaymentStatus.cancelled.equals(payment.getStatus()))
            cancelPurchase(purchase);//chamar função para colocar de volta no estoque
    }

    private PaymentPurchaseTO createPurchase(PaymentTO dto, UserDetailsImpl principal) {
        Customer customer = customerService.getByUserId(principal.getId());
        Purchase purchase = Purchase.builder()
                .customer(customer)
                .shipment(dto.getShipment())
                .totalCost(dto.getShipment())
                .status(PurchaseStatus.PENDING_PAYMENT)
                .paymentRequest(PaymentRequest.builder().buyerId(customer.getId()).build())
                .shipmentAddress(addressService.getById(dto.getShipmentId()))
                .build();

        List<PurchaseItems> items = new ArrayList<>();
        dto.getProducts().stream().forEach(productPaymentTO -> {
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
                    purchase.setTotalCost(purchase.getTotalCost().add(updatedStock.getProductDetail().getPrice()));
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
                .notification_url(WEBHOOK_URL + purchase.getId())
                .expires(true)
                .expiration_date_from(OffsetDateTime.now())
                .expiration_date_to(OffsetDateTime.now().plusMinutes(30))
                .statement_descriptor(STATEMENT)
                .external_reference(purchase.getId().toString())
                .build();

        Preference savedPreference = mercadoPagoAPI.createPreference("Bearer " + TOKEN, preference);
        return savedPreference;
    }

    private void cancelPurchase(Purchase purchase) {
        purchase.setStatus(PurchaseStatus.CANCELED);
        purchase.getItems().parallelStream().forEach(purchaseItems -> stockService.updateQuantity(new UpdateStockTO(purchaseItems.getItem().getId(), purchaseItems.getQuantity())));

    }

}
