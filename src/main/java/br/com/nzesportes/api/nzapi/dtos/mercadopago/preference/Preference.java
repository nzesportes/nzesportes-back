package br.com.nzesportes.api.nzapi.dtos.mercadopago.preference;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class Preference {
    private String id;
    private List<Item> items;
    private Payer payer;
    private BackUrls back_urls;
    private String auto_return;
    private PaymentMethods payment_methods;
    private String notification_url;
    private String statement_descriptor;
    private String external_reference;
    private Boolean expires;
    private OffsetDateTime expiration_date_from;
    private OffsetDateTime  expiration_date_to;
    private String init_point;

}
