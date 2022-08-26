package com.onpu.web.config;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class KafkaTopicConfig {

    TopicsProperties topicsProperties;

    @Bean
    public NewTopic topicUser() {
        return new NewTopic(topicsProperties.getUser(), 1, (short) 1);
    }

    @Bean
    public NewTopic topicComment() {
        return new NewTopic(topicsProperties.getComment(), 1, (short) 1);
    }

    @Bean
    public NewTopic topicMessage() {
        return new NewTopic(topicsProperties.getMessage(), 1, (short) 1);
    }

    @Bean
    public NewTopic topicSubscriber() {
        return new NewTopic(topicsProperties.getSubscriber(), 1, (short) 1);
    }
}
