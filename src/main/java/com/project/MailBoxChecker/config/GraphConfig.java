//package com.project.MailBoxChecker.config;
//
//import com.microsoft.graph.authentication.IAuthenticationProvider;
//import com.microsoft.graph.requests.GraphServiceClient;
//import okhttp3.Request;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
//
//import java.util.concurrent.CompletableFuture;
//
//@Configuration
//public class GraphConfig {
//
//    @Bean
//    public GraphServiceClient<Request> graphClient(
//            OAuth2AuthorizedClientService clientService) {
//
//        OAuth2AuthorizedClient client =
//                clientService.loadAuthorizedClient(
//                        "graph",
//                        "user"
//                );
//
//        String accessToken =
//                client.getAccessToken().getTokenValue();
//
//        IAuthenticationProvider authProvider =
//                requestUrl ->
//                        CompletableFuture.completedFuture(accessToken);
//
//        return GraphServiceClient
//                .builder()
//                .authenticationProvider(authProvider)
//                .buildClient();
//    }
//}