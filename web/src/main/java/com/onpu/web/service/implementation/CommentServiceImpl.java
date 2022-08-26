package com.onpu.web.service.implementation;

import com.google.gson.Gson;
import com.onpu.web.api.dto.CommentDto;
import com.onpu.web.api.dto.EventType;
import com.onpu.web.api.dto.ObjectType;
import com.onpu.web.service.util.WsSender;
import com.onpu.web.config.TopicsProperties;
import com.onpu.web.service.interfaces.CommentService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;
import java.util.function.BiConsumer;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class CommentServiceImpl implements CommentService {

    Gson gson;

    TopicsProperties topicsProperties;

    BiConsumer<EventType, CommentDto> wsSender;

    KafkaTemplate<String, String> kafkaTemplate;


    public CommentServiceImpl(Gson gson, TopicsProperties topicsProperties, KafkaTemplate<String, String> kafkaTemplate, WsSender wsSender) {
        this.gson = gson;
        this.topicsProperties = topicsProperties;
        this.kafkaTemplate = kafkaTemplate;
        this.wsSender = wsSender.getSender(ObjectType.COMMENT);
    }


    @Override
    public CommentDto create(CommentDto comment) {
        String entityId = UUID.randomUUID().toString();
        long timestamp = Instant.now().getEpochSecond();

        comment.setId(entityId);
        comment.setTimestamp(timestamp);
        comment.setEventType(EventType.CREATE);

        kafkaTemplate.send(topicsProperties.getComment(), gson.toJson(comment));
        wsSender.accept(EventType.CREATE, comment);

        return comment;
    }

    @Override
    public void deleteComment(String commentId) {
        CommentDto commentDto = CommentDto.builder()
                .id(commentId)
                .eventType(EventType.REMOVE)
                .build();
        kafkaTemplate.send(topicsProperties.getComment(), gson.toJson(commentDto));
    }
}
