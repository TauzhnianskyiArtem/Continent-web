package com.onpu.web.service.util;

import com.google.gson.Gson;
import com.onpu.web.api.dto.EventType;
import com.onpu.web.api.dto.ObjectType;
import com.onpu.web.api.dto.WsEventDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class WsSender {
    SimpMessagingTemplate template;
    Gson gson;


    public <T> BiConsumer<EventType, T> getSender(ObjectType objectType) {

        return (EventType eventType, T payload) -> {
            String value = gson.toJson(payload);

            template.convertAndSend(
                    "/topic/activity",
                    new WsEventDto(objectType, eventType, value)
            );
        };
    }
}