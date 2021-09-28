package br.com.nzesportes.api.nzapi.services.payment;

import br.com.nzesportes.api.nzapi.domains.customer.Customer;
import br.com.nzesportes.api.nzapi.domains.product.Stock;
import br.com.nzesportes.api.nzapi.domains.purchase.PaymentRequest;
import br.com.nzesportes.api.nzapi.domains.purchase.Purchase;
import br.com.nzesportes.api.nzapi.domains.purchase.PurchaseItems;
import br.com.nzesportes.api.nzapi.domains.purchase.PurchaseStatus;
import br.com.nzesportes.api.nzapi.dtos.product.UpdateStockTO;
import br.com.nzesportes.api.nzapi.dtos.purchase.PaymentTO;
import br.com.nzesportes.api.nzapi.security.services.UserDetailsImpl;
import br.com.nzesportes.api.nzapi.services.customer.CustomerService;
import br.com.nzesportes.api.nzapi.services.product.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private StockService stockService;

    public Purchase createPaymentRequest(PaymentTO dto, UserDetailsImpl principal) {
        return createPurchase(dto, principal);
    }

    private Purchase createPurchase(PaymentTO dto, UserDetailsImpl principal) {
        Customer customer = customerService.getByUserId(principal.getId());
        Purchase purchase = Purchase.builder()
                .customer(customer)
                .shipment(dto.getShipment())
                .totalCost(new BigDecimal("0"))
                .status(PurchaseStatus.PENDING_PAYMENT)
                .paymentRequest(PaymentRequest.builder().buyerId(customer.getId()).build())
                .build();

        List<PurchaseItems> items = new ArrayList<>();
        dto.getProducts().parallelStream().forEach(productPaymentTO -> {
            Stock updatedStock = stockService.updateQuantity(new UpdateStockTO(productPaymentTO.getStockId(), -productPaymentTO.getQuantity()));

            Boolean available = (updatedStock != null && updatedStock.getProductDetail().getStatus());
            if(available)
                purchase.setTotalCost(purchase.getTotalCost().add(updatedStock.getProductDetail().getPrice()));

            items.add(PurchaseItems
                    .builder()
                    .item(updatedStock)
                    .available(available)
                    .quantity(productPaymentTO.getQuantity()).build());
        });
        purchase.setItems(items);
        return purchase;
    }
}
