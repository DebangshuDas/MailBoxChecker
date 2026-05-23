package com.project.MailBoxChecker.controller;

import com.project.MailBoxChecker.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final KafkaProducerService producerService;

    @PostMapping
    public String publish(@RequestParam String message) {

        producerService.sendMessage(message);

        return "Message published successfully";
    }
}