//package com.project.MailBoxChecker.config;
//
//import com.azure.identity.ClientSecretCredential;
//import com.azure.identity.ClientSecretCredentialBuilder;
//import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
//import com.microsoft.graph.requests.GraphServiceClient;
//import okhttp3.Request;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.List;
//
//@Configuration
//public class GraphAuthConfig {
//
//    @Value("${azure.client-id}")
//    private String clientId;
//
//    @Value("${azure.client-secret}")
//    private String clientSecret;
//
//    @Value("${azure.tenant-id}")
//    private String tenantId;
//
//    @Bean
//    public GraphServiceClient<Request> graphClient() {
//
//        ClientSecretCredential credential =
//                new ClientSecretCredentialBuilder()
//                        .clientId(clientId)
//                        .clientSecret(clientSecret)
//                        .tenantId(tenantId)
//                        .build();
//
//        TokenCredentialAuthProvider authProvider =
//                new TokenCredentialAuthProvider(
//                        List.of("https://graph.microsoft.com/.default"),
//                        credential
//                );
//
//        return GraphServiceClient
//                .builder()
//                .authenticationProvider(authProvider)
//                .buildClient();
//    }
//}
