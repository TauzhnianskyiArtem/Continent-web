package ga.continent.api.mapper;

import ga.continent.api.dto.UserReadDto;
import ga.continent.store.entity.UserEntity;
import ga.message.grpc.MessageRead;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<UserReadDto, UserEntity> {

    @Override
    public UserEntity map(UserReadDto object) {
        return UserEntity
                .builder()
                .id(object.getId())
                .userpic(object.getUserpic())
                .name(object.getName())
                .build();
    }

    public UserReadDto mapTo(UserEntity object) {
        return UserReadDto
                .builder()
                .id(object.getId())
                .userpic(object.getUserpic())
                .name(object.getName())
                .build();
    }

    public MessageRead.UserRead mapToUserRead(UserEntity object) {
        return MessageRead.UserRead.newBuilder()
                .setId(object.getId())
                .setUserpic(object.getUserpic())
                .setName(object.getName())
                .build();
    }

    public MessageRead.UserRead mapToUserRead(UserReadDto object) {
        return MessageRead.UserRead.newBuilder()
                .setId(object.getId())
                .setUserpic(object.getUserpic())
                .setName(object.getName())
                .build();
    }


}
