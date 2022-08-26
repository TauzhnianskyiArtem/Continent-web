package com.onpu.web.api.mapper;

import com.onpu.web.api.dto.CommentDto;
import com.onpu.web.api.dto.MessageReadDto;
import com.onpu.web.api.dto.UserReadDto;
import com.onpu.web.store.entity.UserEntity;
import ga.message.grpc.MessageRead;
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
public class MessageReadMapper implements Mapper<MessageRead, MessageReadDto>{

    UserReadMapper userReadMapper;
    CommentMapper commentMapper;

    @Override
    public MessageReadDto map(MessageRead object) {

        UserReadDto author = userReadMapper.mapTo(object.getAuthor());

        List<CommentDto> comments = Optional.ofNullable(object.getCommentsList())
                .map(list -> list.stream().map(commentMapper::map).collect(Collectors.toList()))
                .orElse(new ArrayList<>());


        return MessageReadDto.builder()
                .id(object.getId())
                .text(object.getText())
                .author(author)
                .comments(comments)
                .link(object.getLink())
                .linkTitle(object.getLinkTitle())
                .linkCover(object.getLinkCover())
                .linkDescription(object.getLinkDescription())
                .timestamp(object.getTimestamp())
                .build();
    }
}
