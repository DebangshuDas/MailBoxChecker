package com.project.MailBoxChecker.service;

import com.microsoft.graph.models.Message;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.graph.requests.MessageCollectionPage;
import lombok.RequiredArgsConstructor;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

//@Service
@RequiredArgsConstructor
public class MailPollingService {

    private final GraphServiceClient<Request> graphClient;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${mailbox.user}")
    private String mailboxUser;

    @Scheduled(fixedDelay = 30000)
    public void checkMails() {

        MessageCollectionPage messages =
                graphClient
                        .me()
                        .messages()
                        .buildRequest()
                        .top(10)
                        .get();

        for (Message message : messages.getCurrentPage()) {

            String subject = message.subject;

            if ("CREATE_TICKET".equalsIgnoreCase(subject)) {

                kafkaTemplate.send(
                        "mail-events",
                        message.id
                );

                System.out.println(
                        "Kafka event published for mail: "
                                + message.subject
                );
            }
        }
    }
}
