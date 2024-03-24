package com.pc1.backendrupay.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public SimpleMailMessage constructResetTokenEmail(String contextPath, String token, String userEmail) {
        String url = contextPath + "/api/auth/change-password?token=" + token;
        return constructEmail("Mudança de senha do RU Pay", "Olá, clique neste link para mudar sua senha:" + 
        " \r\n" + url + " \r\n" + "Você possui 24 horas a partir do envio deste email para realizar a mudança,"
        + " após esse período será necessário pedir a mudança de senha novamente.", userEmail);
    }

    private SimpleMailMessage constructEmail(String subject, String body, String userEmail) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(userEmail);
        email.setFrom("rupay.cg@gmail.com");
        return email;
    }
}
