package com.onpu.web.service.implementation;

import com.google.gson.Gson;
import com.onpu.web.api.dto.*;
import com.onpu.web.config.TopicsProperties;
import com.onpu.web.service.feignclient.MessageFeignClient;
import com.onpu.web.service.interfaces.MessageService;
import com.onpu.web.service.interfaces.MetaContentService;
import com.onpu.web.service.util.WsSender;
import com.onpu.web.store.entity.UserEntity;
import com.onpu.web.store.repository.UserSubscriptionRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class MessageServiceImpl implements MessageService {

    Gson gson;

    TopicsProperties topicsProperties;

    MetaContentService metaContentService;

    MessageFeignClient messageFeignClient;

    KafkaTemplate<String, String> kafkaTemplate;

    BiConsumer<EventType, MessageDto> wsSender;

    UserSubscriptionRepository userSubscriptionRepository;


    public MessageServiceImpl(Gson gson, TopicsProperties topicsProperties, MetaContentService metaContentService, MessageFeignClient messageFeignClient, KafkaTemplate<String, String> kafkaTemplate, UserSubscriptionRepository userSubscriptionRepository, WsSender wsSender) {
        this.gson = gson;
        this.topicsProperties = topicsProperties;
        this.metaContentService = metaContentService;
        this.messageFeignClient = messageFeignClient;
        this.kafkaTemplate = kafkaTemplate;
        this.userSubscriptionRepository = userSubscriptionRepository;
        this.wsSender = wsSender.getSender(ObjectType.MESSAGE);
    }
    

    @Override
    public List<MessageReadDto> findForUser(UserEntity userEntity) {

        List<UserEntity> channels = userSubscriptionRepository.findBySubscriber(userEntity.getId());
        List<String> channelsId = channels.stream().map(UserEntity::getId).collect(Collectors.toList());

        return messageFeignClient.findForChannels(new UserChannelsDto(userEntity.getId(), channelsId));

    }


    @Override
    public MessageDto updateMessage(String messageId, MessageDto message) {
        message.setId(messageId);
        message.setEventType(EventType.UPDATE);
        metaContentService.fillMeta(message);
        kafkaTemplate.send(topicsProperties.getMessage(), gson.toJson(message));
        wsSender.accept(EventType.UPDATE, message);
        return message;
    }


    @Override
    public void deleteMessage(String messageId) {
        MessageDto message = MessageDto.builder()
                .id(messageId)
                .eventType(EventType.REMOVE)
                .build();

        kafkaTemplate.send(topicsProperties.getMessage(), gson.toJson(message));
        wsSender.accept(EventType.REMOVE, message);
    }

    @Override
    public MessageDto createMessage(MessageDto message) {

        String entityId = UUID.randomUUID().toString();
        long timestamp = Instant.now().getEpochSecond();

        message.setId(entityId);
        message.setTimestamp(timestamp);
        message.setEventType(EventType.CREATE);
        metaContentService.fillMeta(message);
        kafkaTemplate.send(topicsProperties.getMessage(), gson.toJson(message));

        return message;
    }

}
