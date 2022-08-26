package ga.continent.api.mapper;

import ga.continent.api.dto.CommentDto;
import ga.continent.api.dto.MessageReadDto;
import ga.continent.api.dto.UserReadDto;
import ga.continent.store.entity.MessageEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MessageReadMapper implements Mapper<MessageEntity, MessageReadDto> {

    CommentCreateMapper commentCreateMapper;
    UserReadMapper userReadMapper;


    @Override
    public MessageReadDto map(MessageEntity object) {
        UserReadDto author = userReadMapper.mapTo(object.getAuthor());

        List<CommentDto> comments = Optional.ofNullable(object.getComments())
                .map(list -> list.stream().map(commentCreateMapper::mapTo).collect(Collectors.toList()))
                .orElse(new ArrayList<>());


        return MessageReadDto.builder()
                .id(object.getId())
                .comments(comments)
                .text(object.getText())
                .author(author)
                .link(object.getLink())
                .linkTitle(object.getLinkTitle())
                .linkCover(object.getLinkCover())
                .linkDescription(object.getLinkDescription())
                .timestamp(object.getTimestamp())
                .build();
    }


}
