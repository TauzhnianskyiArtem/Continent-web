package ga.continent.api.mapper;

import ga.continent.api.dto.CommentDto;
import ga.continent.service.interfaces.UserService;
import ga.continent.store.entity.CommentEntity;
import ga.continent.store.entity.UserEntity;
import ga.message.grpc.MessageRead;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CommentCreateMapper implements Mapper<CommentDto, CommentEntity> {

    UserService loggedUserService;

    UserReadMapper userReadMapper;

    @Override
    public CommentEntity map(CommentDto object) {

        UserEntity author = loggedUserService.getUser(object.getAuthor());

        return CommentEntity.builder()
                .id(object.getId())
                .text(object.getText())
                .author(author)
                .timestamp(object.getTimestamp())
                .build();
    }


    public CommentDto mapTo(CommentEntity object) {
        return CommentDto.builder()
                .id(object.getId())
                .text(object.getText())
                .message(object.getMessage().getId())
                .author(userReadMapper.mapTo(object.getAuthor()))
                .timestamp(object.getTimestamp())
                .build();
    }

    public MessageRead.Comment mapToComment(CommentEntity object) {
        return MessageRead.Comment
                .newBuilder()
                .setId(object.getId())
                .setText(object.getText())
                .setMessage(object.getMessage().getId())
                .setAuthor(userReadMapper.mapToUserRead(object.getAuthor()))
                .setTimestamp(object.getTimestamp())
                .build();
    }

    public MessageRead.Comment mapToComment(CommentDto object) {
        return MessageRead.Comment
                .newBuilder()
                .setId(object.getId())
                .setText(object.getText())
                .setMessage(object.getMessage())
                .setAuthor(userReadMapper.mapToUserRead(object.getAuthor()))
                .setTimestamp(object.getTimestamp())
                .build();
    }

}
