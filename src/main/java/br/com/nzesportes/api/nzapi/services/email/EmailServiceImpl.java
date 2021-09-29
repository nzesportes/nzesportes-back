package br.com.nzesportes.api.nzapi.services.email;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${nz.email.from}")
    private String from;

    private JavaMailSender emailSender;

    private EmailContentBuilder emailContentBuilder;

    private String to;
    private String subject;
    private StringBuilder content;
    private String title;
    private String action;
    private String link;
    private Object sales;

    @Autowired
    public EmailServiceImpl(EmailContentBuilder emailContentBuilder, JavaMailSender emailSender) {
        this.emailContentBuilder = emailContentBuilder;
        this.content = new StringBuilder();
        this.emailSender = emailSender;
    }
    public EmailServiceImpl getInstance() 	{
        this.to = "";
        this.subject = "";
        this.content.setLength(0);
        return this;
    }

    @Override
    public boolean send() {
        return this.sendEmailTo(
                this.to,
                this.subject,
                this.content.toString(),
                this.title,
                this.action,
                this.link,
                this.sales
        );
    }

    @Override
    public boolean sendEmailTo(String to, String subject, String text, String title, String action, String link, Object sales) {
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setTo(to);
            message.setFrom(this.from);
            message.setSubject(subject);
            String content = emailContentBuilder.build(text, title, action, link, sales);
            message.setText(content, true);
        };

        try {
            this.emailSender.send(preparator);
           System.out.println("Email has been sent to  "
                    + to + "  Hora: " + LocalDateTime.now());
            return true;
        }
        catch (MailException e) {
            System.out.println("Error while sending email to  "
                    + to + e);
            return false;
        }
    }

    @Override
    public EmailService withTo(String to) {
        this.to +=to;
        return this;
    }

    @Override
    public EmailService withSubject(String subject) {
        this.subject += subject;
        return this;
    }

    @Override
    public EmailService withUrls(String urls) {
        this.content.append(urls);
        return this;
    }
    @Override
    public EmailService withContent(String content) {
        this.content.append(content);
        return this;
    }

    @Override
    public EmailService withTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public EmailService withAction(String action) {
        this.action = action;
        return this;
    }

    @Override
    public EmailService withLink(String link) {
        this.link = link;
        return this;
    }

    @Override
    public EmailService withExchangeTo(Object sales) {
        this.sales = sales;
        return this;
    }


    @Override
    public String getTo() {
        return to;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    @Override
    public String getContent() {
        return content.toString();
    }

    @Override
    public void sendEmailAuth(String toEmail, String link,  boolean isRecovery) {
        if(!isRecovery){
            new Thread(()-> {
                this.
                        getInstance()
                        .withTo(toEmail)
                        .withTitle("Primeiro acesso")
                        .withAction("Criar senha")
                        .withLink(link)
                        .withContent("Bem vindo, para criar a sua senha acesse o link clicando no botão abaixo. ")
                        .withSubject("Nzepesportes - primeiro acesso: criar senha")
                        .send();
            }).start();
        }else {
            new Thread(()-> {
                this.
                        getInstance()
                        .withTo(toEmail)
                        .withTitle("Recuperação Senha")
                        .withAction("Recuperar senha")
                        .withLink(link)
                        .withContent("Olá! você solicitou a alteração da sua senha na Nzesportes. Clique no botão a seguir para alterar a senha.")
                        .withSubject("Nzesportes: Recuperar senha")
                        .send();
            }).start();
        }
    }
}
