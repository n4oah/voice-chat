package com.voicechat.channelinvite.controller;

import com.voicechat.channelinvite.application.ChannelInviteService;
import com.voicechat.channelinvite.dto.InviteChannelDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/")
@RequiredArgsConstructor
public class ChannelInviteController {
    private final ChannelInviteService channelInviteService;

    @PostMapping("/")
    public void inviteChannel(@RequestBody @Valid InviteChannelDto.InviteChannelReqDto inviteChannelReqDto) {
        this.channelInviteService.inviteChannel(inviteChannelReqDto);
    }
}
