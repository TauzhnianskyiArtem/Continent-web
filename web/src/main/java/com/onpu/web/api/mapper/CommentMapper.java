package com.onpu.web.api.mapper;

import com.onpu.web.api.dto.CommentDto;
import com.onpu.web.api.dto.UserReadDto;
import ga.message.grpc.MessageRead;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CommentMapper implements Mapper<MessageRead.Comment, CommentDto>{

    UserReadMapper userReadMapper;

    @Override
    public CommentDto map(MessageRead.Comment object) {
        UserReadDto author = userReadMapper.mapTo(object.getAuthor());

        return CommentDto
                .builder()
                .id(object.getId())
                .message(object.getMessage())
                .text(object.getText())
                .author(author)
                .timestamp(object.getTimestamp())
                .build();
    }
}
