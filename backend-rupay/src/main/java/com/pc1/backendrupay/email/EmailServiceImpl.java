package com.pc1.backendrupay.email;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.pc1.backendrupay.domain.UserModel;

import org.springframework.mail.javamail.JavaMailSender;

@Component
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("luiz.cardozo@ccc.ufcg.edu.br");
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text);
        emailSender.send(message);
    }

    public SimpleMailMessage templateSimpleMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(
        "This is the test email template for your email:\n%s\n");
        return message;
    }

    public SimpleMailMessage constructResetTokenEmail(String contextPath, String token, String userEmail) {
        String url = contextPath + "/auth/changePassword?token=" + token;
        //String message = messages.getMessage("message.resetPassword", null, locale);
        return constructEmail("Reset Password", "Click here to change your password:" + " \r\n" + url, userEmail);
    }

    private SimpleMailMessage constructEmail(String subject, String body, String userEmail) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(userEmail);
        email.setFrom("luiz.cardozo@ccc.ufcg.edu.br");
        return email;
    }
}
