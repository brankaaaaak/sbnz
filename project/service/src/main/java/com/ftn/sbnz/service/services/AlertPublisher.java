package com.ftn.sbnz.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.events.SystemAlert;

@Service
public class AlertPublisher {
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void publish(SystemAlert alert) {
        messagingTemplate.convertAndSend("/topic/alerts", alert);
    }
}
