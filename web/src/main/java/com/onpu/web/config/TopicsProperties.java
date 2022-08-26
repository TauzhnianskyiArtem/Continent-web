package com.onpu.web.config;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Value
@ConstructorBinding
@ConfigurationProperties(prefix = "topics")
public class TopicsProperties{
    String user;
    String message;
    String comment;
    String subscriber;
}