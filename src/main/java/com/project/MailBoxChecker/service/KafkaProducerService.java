package com.project.MailBoxChecker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(String message) {

        kafkaTemplate.send("demo-topic", message);

        System.out.println("Message Sent: " + message);
    }

    public void mailTrigger(String subject) {

        kafkaTemplate.send("mail-events", subject);

        System.out.println("Message Sent: " + subject);
    }
}
