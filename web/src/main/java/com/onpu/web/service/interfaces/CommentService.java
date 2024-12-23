package com.onpu.web.service.interfaces;

import com.onpu.web.api.dto.CommentDto;

public interface CommentService {
    CommentDto create(CommentDto comment);

    void deleteComment(String commentId, String messageId);
}
