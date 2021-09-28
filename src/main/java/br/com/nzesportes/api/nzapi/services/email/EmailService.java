package br.com.nzesportes.api.nzapi.services.email;

public interface EmailService {
    EmailService getInstance();
    boolean send();
    boolean sendEmailTo(String to, String subject, String text, String title, String action, String link, Object sales);
    EmailService withTo(String to);
    EmailService withSubject(String subject);
    EmailService withUrls(String urls);
    EmailService withContent(String content);
    EmailService withTitle(String title);
    EmailService withAction(String action);
    EmailService withLink(String link);
    EmailService withExchangeTo(Object sales );
    String getTo();
    String getSubject();
    String getContent();
    void sendEmailAuth(String toEmail, String link,  boolean isRecovery);
}
