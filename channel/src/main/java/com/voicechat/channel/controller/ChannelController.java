package com.voicechat.channel.controller;

import com.voicechat.channel.application.ChannelService;
import com.voicechat.channel.dto.CreateChannelDto;
import com.voicechat.common.constant.HeaderKey;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;


    @PostMapping()
    public void createChannel(
            @RequestHeader(HeaderKey.USER_ID) final Long userId,
            @Valid @RequestBody final CreateChannelDto.CreateChannelDtoReq createChannelDtoReq
    ) {
        this.channelService.createChannel(userId, createChannelDtoReq);
    }
}
