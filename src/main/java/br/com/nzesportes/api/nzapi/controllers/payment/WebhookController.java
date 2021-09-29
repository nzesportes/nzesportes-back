package br.com.nzesportes.api.nzapi.controllers.payment;

import br.com.nzesportes.api.nzapi.dtos.mercadopago.webhook.PaymentWebhookNotificationTO;
import br.com.nzesportes.api.nzapi.services.payment.WebhookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/webhook")
@CrossOrigin("${nz.allowed.origin}")
public class WebhookController {
    @Autowired
    private WebhookService service;

    @PostMapping("/payment/{id}")
    public void paymentNotification(@PathVariable UUID id, @RequestBody PaymentWebhookNotificationTO webhookNotification) {
        service.paymentNotification(id, webhookNotification);
    }
}
