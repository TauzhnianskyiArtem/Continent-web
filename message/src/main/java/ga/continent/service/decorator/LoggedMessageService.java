package ga.continent.service.decorator;

import ga.continent.api.dto.MessageCreateDto;
import ga.continent.api.dto.MessageReadDto;
import ga.continent.api.dto.UserChannelsDto;
import ga.continent.service.interfaces.MessageService;
import ga.continent.store.entity.MessageEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
@Service
public class LoggedMessageService implements MessageService {

    MessageService cachedMessageService;

    @Override
    public List<MessageReadDto> findForChannels(UserChannelsDto userChannelDto) {
        log.info("Messages for user:" + userChannelDto.getUserId());
        return cachedMessageService.findForChannels(userChannelDto);
    }

    @Override
    public Optional<MessageReadDto> updateMessage(String messageId, MessageCreateDto message) {
        log.info("Message id: " + messageId);
        log.info("Message new text: " + message.getText());

        return cachedMessageService.updateMessage(messageId, message);
    }

    @Override
    public boolean deleteMessage(String messageId) {
        log.info("Message id for delete:" + messageId);

        return cachedMessageService.deleteMessage(messageId);
    }


    @Override
    public MessageReadDto createMessage(MessageCreateDto message) {
        log.info("Create message: ");
        log.info("User id: " + message.getAuthor().getId());
        log.info("Message text: " + message.getText());

        return cachedMessageService.createMessage(message);
    }

    @Override
    public Optional<MessageEntity> findById(String messageId) {
        log.info("Find Message by id: " + messageId);

        return cachedMessageService.findById(messageId);
    }

}
