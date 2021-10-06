package br.com.nzesportes.api.nzapi.dtos.mercadopago.webhook;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
public class PaymentWebhookNotificationTO {
    private BigInteger id;
    private boolean live_mode;
    private String type;
    private LocalDateTime date_created;
    private BigInteger application_id;
    private BigInteger user_id;
    private Integer version;
    private String api_version;
    private String action;
    private WebhookDataTO data;
}
