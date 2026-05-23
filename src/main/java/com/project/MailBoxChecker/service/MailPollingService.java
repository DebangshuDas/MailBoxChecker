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
@Service
@RequiredArgsConstructor
public class MailPollingService {

    private final GraphServiceClient<Request> graphClient;

    private final KafkaTemplate<String, String>
            kafkaTemplate;

    @Value("${mailbox.user}")
    private String mailboxUser;

    @Scheduled(fixedDelay = 30000)
    public void pollMailbox() {

        try {

            MessageCollectionPage messages =
                    graphClient
                            .users(mailboxUser)
                            .messages()
                            .buildRequest()
                            .top(10)
                            .get();

            for(Message message :
                    messages.getCurrentPage()) {

                if(message.subject != null &&
                        message.subject.contains(
                                "CREATE_TICKET")) {

                    kafkaTemplate.send(
                            "mail-events",
                            message.subject
                    );

                    System.out.println(
                            "Kafka event published"
                    );
                }
            }

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }
}