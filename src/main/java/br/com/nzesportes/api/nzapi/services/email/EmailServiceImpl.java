package br.com.nzesportes.api.nzapi.services.email;


import br.com.nzesportes.api.nzapi.domains.purchase.PurchaseItems;
import br.com.nzesportes.api.nzapi.dtos.product.ProductDetailsTO;
import br.com.nzesportes.api.nzapi.services.product.ProductService;
import br.com.nzesportes.api.nzapi.utils.ProductUtils;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${nz.email.from}")
    private String from;

    private JavaMailSender emailSender;

    private EmailContentBuilder emailContentBuilder;

    private ProductService productService;

    private ProductUtils productUtils;

    private String to;
    private String subject;
    private StringBuilder content;
    private String title;
    private String action;
    private String link;
    private List<ProductDetailsTO> products;

    @Autowired
    public EmailServiceImpl(EmailContentBuilder emailContentBuilder, JavaMailSender emailSender, ProductService productService, ProductUtils productUtils) {
        this.emailContentBuilder = emailContentBuilder;
        this.content = new StringBuilder();
        this.emailSender = emailSender;
        this.productService = productService;
        this.productUtils = productUtils;
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
                this.products
        );
    }

    @Override
    public boolean sendEmailTo(String to, String subject, String text, String title, String action, String link, List<ProductDetailsTO> products) {
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setTo(to);
            message.setFrom(this.from);
            message.setSubject(subject);
            String content = emailContentBuilder.build(text, title, action, link, products);
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
    public EmailService withProducts(List<ProductDetailsTO> sales) {
        this.products = sales;
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

    @Override
    public void sendEmailPurchase(String toEmail, String subject, String text, String title, String action, String link, List<PurchaseItems> purchaseItems) {
        List<ProductDetailsTO> items = new ArrayList<>();
        purchaseItems.parallelStream().forEach(product -> {
            ProductDetailsTO p = new ProductDetailsTO();
            p.setId(product.getItem().getProductDetail().getId());
            p.setColor(product.getItem().getProductDetail().getColor());
            p.setDescription(product.getItem().getProductDetail().getDescription());
            p.setImages(StringUtils.split(product.getItem().getProductDetail().getImages(), ";")[0]);
            p.setPrice(product.getItem().getProductDetail().getPrice());
            p.setStatus(product.getItem().getProductDetail().getStatus());
            p.setSize(product.getItem().getSize());
            p.setQuantity(product.getItem().getQuantity());
            p.setProduct(
                this.productUtils.toProductTO(
                    productService.getById(product.getItem().getProductDetail().getProductId())
                )
            );
            items.add(p);
        });

        new Thread(()-> {
            this.
                    getInstance()
                    .withTo(toEmail)
                    .withTitle(title)
                    .withAction(action)
                    .withLink(link)
                    .withContent(text)
                    .withSubject(subject)
                    .withProducts(items)
                    .send();
        }).start();
    }
}
