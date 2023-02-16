package ga.continent.service.implementation;

import ga.continent.api.dto.MessageCreateDto;
import ga.continent.api.dto.MessageReadDto;
import ga.continent.api.dto.UserChannelsDto;
import ga.continent.api.mapper.MessageCreateMapper;
import ga.continent.api.mapper.MessageReadMapper;
import ga.continent.service.interfaces.MessageService;
import ga.continent.store.entity.MessageEntity;
import ga.continent.store.repository.MessageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Transactional(readOnly = true)
@Service
public class MessageServiceImpl implements MessageService {

    MessageReadMapper messageReadMapper;

    MessageCreateMapper messageCreateMapper;

    MessageRepository messageRepository;


    @Override
    public List<MessageReadDto> findForChannels(UserChannelsDto userChannelDto) {
        var usersId = userChannelDto.getChannelsId();
        usersId.add(userChannelDto.getUserId());
        var sortBy = Sort.sort(MessageEntity.class);
        var sort = sortBy.by(MessageEntity::getTimestamp).descending();
        List<MessageEntity> messages = messageRepository.findByAuthorIdIn(usersId, sort);
        return messages.stream().map(messageReadMapper::map).collect(Collectors.toList());
    }


    @Transactional
    @Override
    public Optional<MessageReadDto> updateMessage(String messageId, MessageCreateDto message) {

        return messageRepository.findById(messageId)
                .map(messageFromDb -> {
                    messageFromDb.setText(message.getText());
                    return messageRepository.saveAndFlush(messageFromDb);
                }).map(messageReadMapper::map);
    }


    @Transactional
    @Override
    public boolean deleteMessage(String messageId) {
        return messageRepository.findById(messageId)
                .map(entity -> {
                    messageRepository.delete(entity);
                    messageRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    @Transactional
    @Override
    public MessageReadDto createMessage(MessageCreateDto message) {
        return Optional.of(message)
                .map(messageCreateMapper::map)
                .map(messageRepository::saveAndFlush)
                .map(messageReadMapper::map).orElse(null);
    }


    @Override
    public Optional<MessageEntity> findById(String messageId) {
        return messageRepository.findById(messageId);
    }


}
