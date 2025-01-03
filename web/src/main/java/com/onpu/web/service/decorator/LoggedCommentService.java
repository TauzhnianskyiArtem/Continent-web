package com.onpu.web.service.decorator;

import com.onpu.web.api.dto.CommentDto;
import com.onpu.web.service.interfaces.CommentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
@Service
public class LoggedCommentService implements CommentService {

    CommentService commentServiceImpl;



    @Override
    public CommentDto create(CommentDto comment) {
        log.info("Create comment: ");
        log.info("User id: " + comment.getAuthor().getId());
        log.info("Comment text: " + comment.getText());
        log.info("Message id: " + comment.getMessage());
        return commentServiceImpl.create(comment);
    }

    @Override
    public void deleteComment(String commentId, String messageId) {
        log.info("Comment id for delete:" + commentId);
        commentServiceImpl.deleteComment(commentId, messageId);
    }
}
