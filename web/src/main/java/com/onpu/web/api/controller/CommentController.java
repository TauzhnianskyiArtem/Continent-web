package com.onpu.web.api.controller;

import com.onpu.web.api.dto.CommentDto;
import com.onpu.web.api.dto.UserReadDto;
import com.onpu.web.api.mapper.UserReadMapper;
import com.onpu.web.service.interfaces.CommentService;
import com.onpu.web.service.interfaces.UserService;
import com.onpu.web.service.oauth2.OAuth2User;
import com.onpu.web.store.entity.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CommentController {

    UserService loggedUserService;

    UserReadMapper userMapper;

    CommentService loggedCommentService;

    @PostMapping
    public CommentDto createComment(
            @RequestBody CommentDto comment,
            @AuthenticationPrincipal OAuth2User oauthUser
    ) {

        UserEntity user = loggedUserService.getById(oauthUser.getName());
        UserReadDto userDto = userMapper.map(user);
        comment.setAuthor(userDto);
        return loggedCommentService.create(comment);
   }

    @DeleteMapping("{commentId}/message/{messageId}")
    public void deleteComment(@PathVariable String commentId, @PathVariable String messageId) {
        loggedCommentService.deleteComment(commentId, messageId);
    }
}
