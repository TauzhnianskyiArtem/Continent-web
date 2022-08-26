package ga.continent.service.implementation;

import ga.continent.api.dto.CommentDto;
import ga.continent.api.mapper.CommentCreateMapper;
import ga.continent.service.interfaces.CommentService;
import ga.continent.service.interfaces.MessageService;
import ga.continent.store.entity.MessageEntity;
import ga.continent.store.repository.CommentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class CommentServiceImpl implements CommentService {

    CommentCreateMapper commentCreateMapper;

    MessageService loggedMessageService;

    CommentRepository commentRepository;


    @Transactional
    @Override
    public CommentDto create(CommentDto comment) {
        return Optional.of(comment)
                .map(commentCreateMapper::map)
                .map(entity -> {
                    entity.setMessage(getMessage(comment.getMessage()));
                    return entity;
                })
                .map(commentRepository::saveAndFlush)
                .map(savedComment -> comment).orElse(null);
    }

    @Transactional
    @Override
    public boolean deleteMessage(String commentId) {
        return commentRepository.findById(commentId)
                .map(entity -> {
                    commentRepository.delete(entity);
                    commentRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    private MessageEntity getMessage(String messageId) {
        return Optional.of(messageId)
                .flatMap(loggedMessageService::findById).orElse(null);
    }
}
