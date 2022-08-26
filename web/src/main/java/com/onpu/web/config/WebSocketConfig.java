package com.onpu.web.config;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private static final String[] APP_PREFIXES = new String[]{"/app"};
    private static final String[] BROKER_PREFIXES = new String[]{"/topic"};
    public static final int MAX_WORKERS_COUNT = Math.max(2, Runtime.getRuntime().availableProcessors());
    public static final int TASK_QUEUE_SIZE = 10_000;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setPreservePublishOrder(true)
                .setApplicationDestinationPrefixes(APP_PREFIXES)
                .enableStompBrokerRelay(BROKER_PREFIXES)
                .setTaskScheduler(getHeartbeatScheduler());

        registry.configureBrokerChannel()
                .taskExecutor().corePoolSize(1).maxPoolSize(MAX_WORKERS_COUNT).queueCapacity(TASK_QUEUE_SIZE);
    }


    @CrossOrigin
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket").setAllowedOrigins("http://localhost:9000").withSockJS();
    }

    @Bean
    public Gson gson(){
        return new Gson();
    }

    @Bean
    public TaskScheduler getHeartbeatScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(1);
        scheduler.initialize();

        return scheduler;
    }

}
