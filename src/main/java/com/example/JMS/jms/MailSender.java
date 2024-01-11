package com.example.JMS.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class MailSender {
    @Value("${spring.mail.username}")
    private  String from;

    @Autowired
    private JavaMailSender emailSender;

    public void sendMessage(String toEmail, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(toEmail);
        message.setText(text);
        emailSender.send(message);
    }
}