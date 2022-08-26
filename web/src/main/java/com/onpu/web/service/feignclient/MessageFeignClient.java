package com.onpu.web.service.feignclient;

import com.onpu.web.api.dto.ChannelsDto;
import com.onpu.web.api.dto.MessageReadDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "message", url = "${gateway.url}")
public interface MessageFeignClient {

    @PostMapping("/message/channels")
    List<MessageReadDto> findForChannels(ChannelsDto channelsDto);
}