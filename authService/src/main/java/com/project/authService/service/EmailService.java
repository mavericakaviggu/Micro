package com.project.authService.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
public class EmailService {

    // private final JavaMailSender mailSender;
    // @Value("${spring.mail.properties.mail.smtp.from}") // value is fetched from application.yaml
    // // This property should be set in application.yaml or application.properties
    private String fromEmail;
    
    public void sendWelcomeEmail(String toEmail, String name) {
        // Dummy mock implementation (no real mail sending)
        System.out.printf("Mock Email sent to %s. Welcome %s!%n", toEmail, name);

        // SimpleMailMessage message = new SimpleMailMessage();
        // message.setFrom(fromEmail);
        // message.setTo(toEmail);
        // message.setSubject("Welcome to our platform, " + name + "!");
        // message.setText("Hello " + name + ",\n\n" +
        //                 "Thank you for signing up! We're excited to have you on board.\n\n" +
        //                 "Best regards,\n" +
        //                 "The Team");
         
        // mailSender.send(message);
    }

}
