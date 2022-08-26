package ga.continent.api.controller;


import ga.continent.api.dto.ChannelsDto;
import ga.continent.api.dto.MessageReadDto;
import ga.continent.service.interfaces.MessageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/messages")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MessageController {

    MessageService loggedMessageService;


    @PostMapping("/channels")
    public List<MessageReadDto> findForChannels(@RequestBody ChannelsDto channelsDto) {

        return loggedMessageService.findForChannels(channelsDto);
    }
}
