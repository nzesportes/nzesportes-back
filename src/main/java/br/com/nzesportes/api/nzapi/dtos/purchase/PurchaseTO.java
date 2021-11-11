package br.com.nzesportes.api.nzapi.dtos.purchase;

import br.com.nzesportes.api.nzapi.domains.purchase.MercadoPagoPaymentStatus;
import br.com.nzesportes.api.nzapi.dtos.customer.AddressTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PurchaseTO {
    private UUID id;
    private List<PurchaseItemsTO> items;
    private BigDecimal shipment;
    private AddressTO shipmentAddress;
    private BigDecimal totalCost;
    private MercadoPagoPaymentStatus status;
    private PaymentRequestTO paymentRequest;
}
