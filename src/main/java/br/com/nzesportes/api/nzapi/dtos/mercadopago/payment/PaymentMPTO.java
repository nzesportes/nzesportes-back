package br.com.nzesportes.api.nzapi.dtos.mercadopago.payment;

import br.com.nzesportes.api.nzapi.domains.purchase.MercadoPagoPaymentStatus;
import br.com.nzesportes.api.nzapi.dtos.mercadopago.preference.Payer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
public class PaymentMPTO {
    public int id;
    public OffsetDateTime date_created;
    public OffsetDateTime date_approved;
    public OffsetDateTime date_last_updated;
    public OffsetDateTime money_release_date;
    public String payment_method_id;
    public String payment_type_id;
    public MercadoPagoPaymentStatus status;
    public String status_detail;
    public String currency_id;
    public String description;
    public Integer collector_id;
    public Payer payer;
    public Object metadata;
    public Object additional_info;
    public Object order;
    public Integer transaction_amount;
    public Integer transaction_amount_refunded;
    public Integer coupon_amount;
    public TransactionDetails transaction_details;
    public Integer installments;
    public Object card;
}
