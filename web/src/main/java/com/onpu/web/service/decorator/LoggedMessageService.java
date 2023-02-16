package com.onpu.web.service.decorator;

import com.onpu.web.api.dto.MessageDto;
import com.onpu.web.api.dto.MessageReadDto;
import com.onpu.web.service.interfaces.MessageService;
import com.onpu.web.store.entity.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
@Service
public class LoggedMessageService implements MessageService{

    MessageService messageServiceImpl;

    @Override
    public List<MessageReadDto> findForUser(UserEntity userEntity) {
        log.info("Messages for user:");
        log.info("User id: " + userEntity.getId());
        log.info("User name: " + userEntity.getName());

        return messageServiceImpl.findForUser(userEntity);
    }

    @Override
    public MessageDto updateMessage(String messageId, MessageDto message) {
        log.info("Message id: " + messageId);
        log.info("Message new text: " + message.getText());

        return messageServiceImpl.updateMessage(messageId, message);
    }

    @Override
    public void deleteMessage(String messageId) {
        log.info("Message id for delete:" + messageId);

        messageServiceImpl.deleteMessage(messageId);
    }


    @Override
    public MessageDto createMessage(MessageDto message) {
        log.info("Create message: ");
        log.info("User id: " + message.getAuthor().getId());
        log.info("Message text: " + message.getText());

        return messageServiceImpl.createMessage(message);
    }


}
