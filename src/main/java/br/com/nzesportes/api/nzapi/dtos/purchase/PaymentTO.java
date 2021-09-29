package br.com.nzesportes.api.nzapi.dtos.purchase;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PaymentTO {
    private List<ProductPaymentTO> products;
    private BigDecimal shipment;
    private UUID shipmentId;
}
