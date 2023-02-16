package ga.continent.service.decorator;

import com.google.gson.Gson;
import ga.continent.api.dto.MessageCreateDto;
import ga.continent.api.dto.MessageReadDto;
import ga.continent.api.dto.UserChannelsDto;
import ga.continent.service.interfaces.MessageService;
import ga.continent.service.util.CacheHelper;
import ga.continent.store.entity.MessageEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ga.continent.config.CacheConfig.MESSAGES_CACHE;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class CachedMessageService implements MessageService {

    Gson gson;

    Cache messagesCache;

    CacheHelper cacheHelper;

    MessageService messageServiceImpl;



    public CachedMessageService(Gson gson, MessageService messageServiceImpl, CacheManager cacheManager, CacheHelper cacheHelper) {
        this.gson = gson;
        this.messageServiceImpl = messageServiceImpl;
        this.messagesCache = cacheManager.getCache(MESSAGES_CACHE);
        this.cacheHelper = cacheHelper;
    }

    @Override
    public List<MessageReadDto> findForChannels(UserChannelsDto userChannelDto) {
        if(!Objects.isNull(messagesCache.get(userChannelDto.getUserId()))) {
            final MessageReadDto[] messagesReadDto = gson.fromJson(messagesCache.get(userChannelDto.getUserId(), String.class), MessageReadDto[].class);
            return List.of(messagesReadDto);
        }
        List<MessageReadDto> messages = messageServiceImpl.findForChannels(userChannelDto);
        String messagesJson = gson.toJson(messages);
        messagesCache.putIfAbsent(userChannelDto.getUserId(), messagesJson);
        return messages;
    }

    @Override
    public Optional<MessageReadDto> updateMessage(String messageId, MessageCreateDto message) {
        cacheHelper.evictCacheBy((userId, messages) -> messages.stream()
                .map(MessageReadDto::getId)
                .anyMatch(id -> id.equals(messageId)));
        return messageServiceImpl.updateMessage(messageId, message);
    }


    @Override
    public boolean deleteMessage(String messageId) {
        cacheHelper.evictCacheBy((userId, messages) -> messages.stream()
                .map(MessageReadDto::getId)
                .anyMatch(id -> id.equals(messageId)));

        return messageServiceImpl.deleteMessage(messageId);
    }


    @Override
    public MessageReadDto createMessage(MessageCreateDto message) {

        cacheHelper.evictCacheBy((userId, messages) ->
                userId.equals(message.getAuthor().getId()) ||
                        message.getAuthor().getSubscribers().contains(userId));

        return messageServiceImpl.createMessage(message);
    }

    @Override
    public Optional<MessageEntity> findById(String messageId) {
        return messageServiceImpl.findById(messageId);
    }


    public void evictSubscriber(String subscriberId){
        messagesCache.evictIfPresent(subscriberId);
    }

}
