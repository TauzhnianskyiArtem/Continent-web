package ga.continent.service.interfaces;

import ga.continent.api.dto.ChannelsDto;
import ga.continent.api.dto.MessageCreateDto;
import ga.continent.api.dto.MessageReadDto;
import ga.continent.store.entity.MessageEntity;

import java.util.List;
import java.util.Optional;

public interface MessageService {

    List<MessageReadDto> findForChannels(ChannelsDto userChannelDto);

    Optional<MessageReadDto> updateMessage(String messageId, MessageCreateDto message);

    boolean deleteMessage(String messageId);

    MessageReadDto createMessage(MessageCreateDto message);

    Optional<MessageEntity> findById(String messageId);

}
