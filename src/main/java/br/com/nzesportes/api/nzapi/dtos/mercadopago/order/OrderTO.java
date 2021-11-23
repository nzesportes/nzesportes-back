package br.com.nzesportes.api.nzapi.dtos.mercadopago.order;

import br.com.nzesportes.api.nzapi.dtos.mercadopago.preference.Payer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class OrderTO {
    private BigInteger id;
    private OrderStatus status;
    private String external_reference;
    private String preference_id;
    private List<OrderPaymentTO> payments;
    private List<Object> shipments;
    private List<Object> payouts;
    private CollectorTO collector;
    private String marketplace;
    private String notification_url;
    private OffsetDateTime date_created;
    private OffsetDateTime last_updated;
    private Object sponsor_id;
    private BigDecimal shipping_cost;
    private BigDecimal total_amount;
    private String site_id;
    private BigDecimal paid_amount;
    private BigDecimal refunded_amount;
    private Payer payer;
    private List<ItemTO> items;
    private boolean cancelled;
    private String additional_info;
    private Object application_id;
    private OrderPaymentStatus order_status;
}
