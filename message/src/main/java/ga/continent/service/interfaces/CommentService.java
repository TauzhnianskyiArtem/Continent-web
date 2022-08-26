package ga.continent.service.interfaces;

import ga.continent.api.dto.CommentDto;

public interface CommentService {
    CommentDto create(CommentDto comment);

    boolean deleteMessage(String commentId);
}
