package com.pc1.backendrupay.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmailFromTemplate(String url, String email) throws MessagingException, FileNotFoundException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        message.setFrom(new InternetAddress("rupay.cg@gmail.com"));
        message.setRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("Mudança de senha do RU Pay", "utf-8");

        // Read the HTML template into a String variable
        String htmlTemplate = readFile("src\\main\\resources\\email-templates\\emailRecuperacaoSenha.html");

        // Replace placeholders in the HTML template with dynamic values
        htmlTemplate = htmlTemplate.replace("${link}", url);

        // Set the email's content to be the HTML template
        message.setContent(htmlTemplate, "text/html; charset=utf-8");
        //helper.setText(htmlTemplate, true);

        mailSender.send(message);
    }

    private String readFile(String path) throws FileNotFoundException {
        /* Constructing String Builder to
        append the string into the html */
        StringBuilder html = new StringBuilder();
        String result = "";
 
        // Reading html file on local directory
        FileReader fr = new FileReader(path);
 
        // Try block to check exceptions
        try {
 
            // Initialization of the buffered Reader to get
            // the String append to the String Builder
            BufferedReader br = new BufferedReader(fr);
 
            String val;
 
            // Reading the String till we get the null
            // string and appending to the string
            while ((val = br.readLine()) != null) {
                html.append(val);
            }
 
            // AtLast converting into the string
            result = html.toString();
 
            // Closing the file after all the completion of
            // Extracting
            br.close();
        }
 
        // Catch block to handle exceptions
        catch (Exception ex) {
 
            /* Exception of not finding the location and
            string reading termination the function
            br.close(); */
            System.out.println(ex.getMessage());
        }
        return result;
    }

    public void constructResetTokenEmail(String contextPath, String token, String userEmail) throws MessagingException, FileNotFoundException{
        String url = contextPath + "/api/auth/change-password?token=" + token;
        /*return constructEmail("Mudança de senha do RU Pay", "Olá, clique neste link para mudar sua senha:" + 
        " \r\n" + url + " \r\n" + "Você possui 24 horas a partir do envio deste email para realizar a mudança,"
        + " após esse período será necessário pedir a mudança de senha novamente.", userEmail); */
        sendEmailFromTemplate(url, userEmail);
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
