package br.com.nzesportes.api.nzapi.services.payment;

import br.com.nzesportes.api.nzapi.dtos.purchase.PaymentWebhookNotificationTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WebhookService {

    public void paymentNotification(UUID id, PaymentWebhookNotificationTO webhookNotification) {

    }

}
