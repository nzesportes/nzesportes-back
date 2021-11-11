package br.com.nzesportes.api.nzapi.services.payment;

import br.com.nzesportes.api.nzapi.domains.purchase.Purchase;
import br.com.nzesportes.api.nzapi.dtos.mercadopago.webhook.PaymentWebhookNotificationTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class WebhookService {
    private final static String PAYMENT_CREATED = "payment.created";
    private final static String PAYMENT_UPDATED = "payment.updated";

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private PaymentService paymentService;

    public void paymentNotification(UUID id, PaymentWebhookNotificationTO webhookNotification) {
        Purchase purchase = purchaseService.getById(id);
        if(PAYMENT_CREATED.equals(webhookNotification.getAction())){
            purchase.getPaymentRequest().setPaymentId(webhookNotification.getData().getId());
            purchase.getPaymentRequest().setCreationDate(LocalDateTime.now());
            purchase = purchaseService.save(purchase);
            paymentService.checkPaymentStatus(purchase);
        } else if (PAYMENT_UPDATED.equals(webhookNotification.getAction()))
            paymentService.checkPaymentStatus(purchase);
    }
    //criar cenário de notificação de merchat order
    //adicionar order_status de merchat order ao enum do backend
}
