package br.com.nzesportes.api.nzapi.services.email;

import br.com.nzesportes.api.nzapi.domains.purchase.Purchase;
import br.com.nzesportes.api.nzapi.domains.purchase.PurchaseItems;
import br.com.nzesportes.api.nzapi.dtos.product.ProductDetailsTO;

import java.util.List;

public interface EmailService {
    EmailService getInstance();
    boolean send();
    boolean sendEmailTo(String to, String subject, String text, String title, String action, String link, List<ProductDetailsTO> sales, Purchase purchase);
    EmailService withTo(String to);
    EmailService withSubject(String subject);
    EmailService withUrls(String urls);
    EmailService withContent(String content);
    EmailService withTitle(String title);
    EmailService withAction(String action);
    EmailService withLink(String link);
    EmailService withProducts(List<ProductDetailsTO> productsDetails );
    EmailService withPurchase(Purchase purchase );

    String getTo();
    String getSubject();
    String getContent();
    void sendEmailAuth(String toEmail, String link,  boolean isRecovery);
    void sendEmailPurchase(String toEmail, String subject, String text, String title, String action, String link, Purchase purchase);
}
