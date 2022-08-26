package ga.continent.api.mapper;

import ga.continent.api.dto.MessageCreateDto;
import ga.continent.api.dto.UserReadDto;
import ga.continent.service.interfaces.UserService;
import ga.continent.store.entity.MessageEntity;
import ga.continent.store.entity.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MessageCreateMapper implements Mapper<MessageCreateDto, MessageEntity> {

    UserService loggedUserService;

    UserSubscribersMapper userSubscribersMapper;

    @Override
    public MessageEntity map(MessageCreateDto object) {

        UserReadDto userReadDto = userSubscribersMapper.map(object.getAuthor());
        UserEntity author = loggedUserService.getUser(userReadDto);

        return MessageEntity.builder()
                .id(object.getId())
                .text(object.getText())
                .author(author)
                .timestamp(object.getTimestamp())
                .linkTitle(object.getLinkTitle())
                .link(object.getLink())
                .linkCover(object.getLinkCover())
                .linkDescription(object.getLinkDescription())
                .build();
    }

}
