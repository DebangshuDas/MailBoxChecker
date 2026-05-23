package com.project.MailBoxChecker.controller;

import com.microsoft.graph.authentication.IAuthenticationProvider;
import com.microsoft.graph.models.Message;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.graph.requests.MessageCollectionPage;

import com.project.MailBoxChecker.service.KafkaConsumerService;
import com.project.MailBoxChecker.service.KafkaProducerService;
import okhttp3.Request;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class MailController {

    private final OAuth2AuthorizedClientService clientService;
    private final KafkaProducerService service;

    public MailController(
            OAuth2AuthorizedClientService clientService, KafkaProducerService service) {

        this.clientService = clientService;
        this.service = service;
    }

//    @GetMapping("/test-mails")
    public String testMails(
            @AuthenticationPrincipal OAuth2User principal) {

        OAuth2AuthorizedClient client =
                clientService.loadAuthorizedClient(
                        "graph",
                        principal.getName()
                );

        String accessToken =
                client.getAccessToken().getTokenValue();

        IAuthenticationProvider authProvider =
                requestUrl ->
                        CompletableFuture.completedFuture(
                                accessToken
                        );

        GraphServiceClient<Request> graphClient =
                GraphServiceClient
                        .builder()
                        .authenticationProvider(authProvider)
                        .buildClient();

        MessageCollectionPage messages =
                graphClient
                        .me()
                        .messages()
                        .buildRequest()
                        .top(5)
                        .get();

        StringBuilder response = new StringBuilder();

        for (Message message :
                messages.getCurrentPage()) {

            response.append(message.subject)
                    .append("\n");
        }

        return response.toString();
    }

    @GetMapping("/test-mails")
    public String testMails2(
            @AuthenticationPrincipal OAuth2User principal) {

        OAuth2AuthorizedClient client =
                clientService.loadAuthorizedClient(
                        "graph",
                        principal.getName()
                );

        String accessToken =
                client.getAccessToken().getTokenValue();

        IAuthenticationProvider authProvider =
                requestUrl ->
                        CompletableFuture.completedFuture(
                                accessToken
                        );

        GraphServiceClient<Request> graphClient =
                GraphServiceClient
                        .builder()
                        .authenticationProvider(authProvider)
                        .buildClient();

        MessageCollectionPage messages =
                graphClient
                        .me()
                        .messages()
                        .buildRequest()
                        .top(10)
                        .get();

        StringBuilder response =
                new StringBuilder();

        response.append("MAIL COUNT: ")
                .append(messages.getCurrentPage().size())
                .append("\n\n");

        for (Message message :
                messages.getCurrentPage()) {

            response.append("SUBJECT: ")
                    .append(message.subject)
                    .append("\n");
            if(message.subject != null && message.subject.equalsIgnoreCase("CREATE_TICKET")) {

                service.mailTrigger(message.subject);
            }

            response.append("FROM: ");

            if(message.from != null &&
                    message.from.emailAddress != null) {

                response.append(
                        message.from.emailAddress.address
                );
            }

            response.append("\n\n");
        }

        return response.toString();
    }
}
