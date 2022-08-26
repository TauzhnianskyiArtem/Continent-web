package com.onpu.web.api.controller;

import com.onpu.web.api.dto.MessageDto;
import com.onpu.web.api.dto.MessageReadDto;
import com.onpu.web.api.dto.UserSubscribersDto;
import com.onpu.web.api.mapper.UserSubscribersMapper;
import com.onpu.web.service.oauth2.OAuth2User;
import com.onpu.web.service.interfaces.MessageService;
import com.onpu.web.service.interfaces.UserService;
import com.onpu.web.store.entity.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/messages")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MessageController {

    UserService loggedUserService;

    UserSubscribersMapper userMapper;

    MessageService loggedMessageService;


    @GetMapping
    public List<MessageReadDto> findForUser(
            @AuthenticationPrincipal OAuth2User oauthUser) {

        return loggedMessageService.findForUser(oauthUser.getUser());
    }


    @PostMapping
    public MessageDto addMessage(
            @RequestBody MessageDto message,
            @AuthenticationPrincipal OAuth2User oauthUser) {

        message.setAuthor(getUserDto(oauthUser));

        return loggedMessageService.createMessage(message);
    }

    @PutMapping("{message_id}")
    public MessageDto updateMessage(
            @PathVariable("message_id") String messageId,
            @RequestBody MessageDto message,
            @AuthenticationPrincipal OAuth2User oauthUser) {


        message.setAuthor(getUserDto(oauthUser));

        return loggedMessageService.updateMessage(messageId, message);
    }

    @DeleteMapping("{message_id}")
    public void deleteMessage(@PathVariable("message_id") String messageId) {
        loggedMessageService.deleteMessage(messageId);
    }

    private UserSubscribersDto getUserDto(OAuth2User oauthUser) {
        UserEntity user = loggedUserService.getById(oauthUser.getName());
        UserSubscribersDto userDto = userMapper.map(user);
        return userDto;
    }

}
