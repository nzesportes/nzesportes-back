package br.com.nzesportes.api.nzapi.dtos.purchase;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PaymentWebhookNotificationTO {
    private Integer id;
    private boolean live_mode;
    private String type;
    private LocalDateTime date_created;
    private Integer application_id;
    private Integer user_id;
    private Integer version;
    private String api_version;
    private String action;
    private WebhookDataTO data;
}
