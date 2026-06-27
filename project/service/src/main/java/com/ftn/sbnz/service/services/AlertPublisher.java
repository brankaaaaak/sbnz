package com.ftn.sbnz.service.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.events.SystemAlert;

@Service
public class AlertPublisher {
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void publish(SystemAlert alert) {
        publishActive(alert.getType());
    }

    public void publishActive(String type) {
        messagingTemplate.convertAndSend("/topic/alerts", Map.of(
                "type", type,
                "active", true));
    }

    public void publishResolved(String type) {
        messagingTemplate.convertAndSend("/topic/alerts", Map.of(
                "type", type,
                "active", false));
    }
}
