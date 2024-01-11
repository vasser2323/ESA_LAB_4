package com.example.JMS.jms;


import com.example.JMS.models.EventType;
import com.example.JMS.models.Logging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {
    @Autowired
    private JmsTemplate jmsTemplate;

    public <T> void sendEvent(T entity, EventType eventType, Integer id) {
        Logging event = new Logging();
        event.setEventType(eventType);
        event.setEntity(entity.toString());
        event.setEntity_id(id);

        jmsTemplate.convertAndSend("logging", event);
        jmsTemplate.convertAndSend("mail", event);
    }
}