package br.com.nzesportes.api.nzapi.dtos.mercadopago.order;

import br.com.nzesportes.api.nzapi.domains.purchase.MercadoPagoPaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
public class OrderPaymentTO {
    private String id;
    private BigDecimal transaction_amount;
    private BigDecimal total_paid_amount;
    private BigDecimal shipping_cost;
    private String currency_id;
    private MercadoPagoPaymentStatus status;
    private String status_detail;
    private String operation_type;
    private OffsetDateTime date_approved;
    private OffsetDateTime date_created;
    private OffsetDateTime last_modified;
    private BigDecimal amount_refunded;
}
