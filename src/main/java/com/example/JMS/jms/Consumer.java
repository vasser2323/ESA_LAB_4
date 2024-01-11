package com.example.JMS.jms;

import com.example.JMS.models.EventType;
import com.example.JMS.models.Logging;
import com.example.JMS.repositories.LoggingRepository;
import com.example.JMS.repositories.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private MailRepository mailRepository;

    @Autowired
    private LoggingRepository loggingRepository;


    @JmsListener(destination = "logging", containerFactory = "factoryContainerConfig")
    public void consumeLogging(Logging log) {
        if (log.getEventType() == EventType.DELETE_CARD) {
            loggingRepository.findAll().forEach(
                    logging -> {
                        if (logging.getEntity_id() == log.getEntity_id() || logging.getEventType() == EventType.DELETE_CARD) {
                            logging.setEventType(EventType.DELETE_CARD);
                            loggingRepository.save(logging);
                            return;
                        }
                    }
            );
            return;
        }
        loggingRepository.save(log);
    }

    @JmsListener(destination = "mail", containerFactory = "factoryContainerConfig")
    public void consumeMail(Logging event) {
        var mails = mailRepository.findAll();
        for (var mail : mails) {
            var conditions = mail.getCondition();
            if (conditions.contains(event.getEventType())) {
                sendMessage(mail.getMail(), "Было изменение в объекте " + event.getEntity()
                        + " действие - " + event.getEventType());
            }
        }
    }
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
