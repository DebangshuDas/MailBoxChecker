package com.project.MailBoxChecker.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic demoTopic() {

        return TopicBuilder
                .name("demo-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic mailTopic() {

        return TopicBuilder
                .name("mail-events")
                .partitions(3)
                .replicas(1)
                .build();
    }
}