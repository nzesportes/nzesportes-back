package br.com.nzesportes.api.nzapi.services.payment;

import br.com.nzesportes.api.nzapi.domains.customer.Customer;
import br.com.nzesportes.api.nzapi.domains.product.Stock;
import br.com.nzesportes.api.nzapi.domains.purchase.Purchase;
import br.com.nzesportes.api.nzapi.domains.purchase.PurchaseItems;
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

    public Object createPaymentRequest(PaymentTO dto, UserDetailsImpl principal) {
        createPurchase(dto, principal);
        return null;
    }

    private void createPurchase(PaymentTO dto, UserDetailsImpl principal) {
        Customer customer = customerService.getByUserId(principal.getId());
        Purchase purchase = Purchase.builder().customer(customer).shipment(dto.getShipment()).totalCost(new BigDecimal("0")).build();

        List<PurchaseItems> items = new ArrayList<>();
        dto.getProducts().parallelStream().forEach(productPaymentTO -> {
            Stock stock = stockService.getById(productPaymentTO.getStockId());
            stock.setQuantity(stock.getQuantity() - productPaymentTO.getQuantity());
            purchase.setTotalCost(purchase.getTotalCost().add(stock.getProductDetail().getPrice()));
            items.add(PurchaseItems
                    .builder()
                    .item(stockService.getById(productPaymentTO.getStockId()))
                    .quantity(productPaymentTO.getQuantity()).build());
        });
    }

}
