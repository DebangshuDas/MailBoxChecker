package com.project.MailBoxChecker.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MailEventConsumer {

    @KafkaListener(
            topics = "mail-events",
            groupId = "mail-group"
    )
    public void consume(String messageId) {

        System.out.println(
                "Processing Mail ID: " + messageId
        );

        // Trigger service method
        System.out.println("Automation Triggered...");
    }
}