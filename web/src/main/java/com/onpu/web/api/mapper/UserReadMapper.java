package com.onpu.web.api.mapper;

import com.onpu.web.api.dto.UserReadDto;
import com.onpu.web.store.entity.UserEntity;
import ga.message.grpc.MessageRead;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<UserEntity, UserReadDto>{
    @Override
    public UserReadDto map(UserEntity object) {
        return UserReadDto
                .builder()
                .id(object.getId())
                .name(object.getName())
                .userpic(object.getUserpic())
                .build();
    }

    public UserReadDto mapTo(MessageRead.UserRead author) {
        return UserReadDto
                .builder()
                .id(author.getId())
                .name(author.getName())
                .userpic(author.getUserpic())
                .build();
    }
}
