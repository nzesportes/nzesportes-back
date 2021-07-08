package br.com.nzesportes.api.nzapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    
    @Value("${nz.email.from}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    public Boolean sendEmail(String to, String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setText(text);
        message.setTo(to);
        message.setFrom(from);

        try {
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
