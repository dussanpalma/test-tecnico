package com.farmatodo.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

class EmailServiceImplTest {

    private JavaMailSender mailSender;
    private EmailServiceImpl emailService;

    @BeforeEach
    void setUp() {
        mailSender = Mockito.mock(JavaMailSender.class);
        emailService = new EmailServiceImpl(mailSender);
    }

    @Test
    void testSendEmail() {
        String to = "test@example.com";
        String subject = "Prueba";
        String body = "Este es un correo de prueba";
        emailService.sendEmail(to, subject, body);
        verifyNoInteractions(mailSender);
    }
}
