package com.onpu.web.service.feignclient;

import com.onpu.web.api.dto.UserChannelsDto;
import com.onpu.web.api.dto.MessageReadDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "message", url = "${gateway.url}")
public interface MessageFeignClient {

    @PostMapping("/channels")
    List<MessageReadDto> findForChannels(UserChannelsDto userChannelsDto);
}