package com.onpu.web.service.interfaces;

import com.onpu.web.api.dto.MessageDto;
import com.onpu.web.api.dto.MessageReadDto;
import com.onpu.web.store.entity.UserEntity;

import java.util.List;

public interface MessageService {

    List<MessageReadDto> findForUser(UserEntity userEntity);

    MessageDto updateMessage(String messageId, MessageDto message);

    void deleteMessage(String messageId);

    MessageDto createMessage(MessageDto message);

}
