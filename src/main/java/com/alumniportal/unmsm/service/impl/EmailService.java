package com.alumniportal.unmsm.service.impl;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;
import java.util.Properties;

@Service
public class EmailService {
    private final String username = "alumnoportal123@gmail.com";
    private final String password = "hjbfkiywctmlpohv";

    public void sendEmail(String to, String subject, String htmlContent) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        System.out.println("Iniciando configuración de correo...");
        System.out.println("Destinatario: " + to);
        System.out.println("Asunto: " + subject);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            System.out.println("Creando mensaje de correo...");
            Message message = new MimeMessage(session);

            // Configuración del remitente
            System.out.println("Configurando remitente: " + username);
            message.setFrom(new InternetAddress(username));

            // Configuración del destinatario
            System.out.println("Configurando destinatario: " + to);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            // Configuración del asunto
            message.setSubject(subject);

            // Configuración del contenido
            System.out.println("Configurando contenido HTML");
            message.setContent(htmlContent, "text/html; charset=utf-8");

            // Debug detallado
            session.setDebug(true);

            System.out.println("Intentando enviar correo...");
            Transport.send(message);
            System.out.println("¡Correo enviado exitosamente!");

        } catch (MessagingException e) {
            System.err.println("Error detallado al enviar el correo:");
            System.err.println("Tipo de error: " + e.getClass().getName());
            System.err.println("Mensaje de error: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("Causa raíz: " + e.getCause().getMessage());
            }
            e.printStackTrace();
            throw new RuntimeException("Error al enviar el correo: " + e.getMessage(), e);
        }
    }
}