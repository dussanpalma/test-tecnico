package com.farmatodo.service.impl;

import com.farmatodo.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(String to, String subject, String body) {
        System.out.println("===Correo enviado ===");
        System.out.println("Para: " + to);
        System.out.println("Asunto: " + subject);
        System.out.println("Cuerpo: " + body);
        System.out.println("============================");
    }

}
