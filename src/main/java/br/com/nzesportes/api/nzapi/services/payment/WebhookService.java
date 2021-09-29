package br.com.nzesportes.api.nzapi.services.payment;

import br.com.nzesportes.api.nzapi.domains.purchase.Purchase;
import br.com.nzesportes.api.nzapi.dtos.mercadopago.webhook.PaymentWebhookNotificationTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WebhookService {
    private final static String PAYMENT_CREATED = "payment.created";

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private PaymentService paymentService;

    public void paymentNotification(UUID id, PaymentWebhookNotificationTO webhookNotification) {
        if(PAYMENT_CREATED.equals(webhookNotification.getAction())){
            Purchase purchase = purchaseService.getById(id);
            purchase.getPaymentRequest().setPaymentId(webhookNotification.getData().getId());
            purchase = purchaseService.save(purchase);
            paymentService.checkPaymentStatus(purchase);
        }
    }

}
