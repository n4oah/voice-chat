package com.voicechat.channelinvite.controller;

import com.voicechat.channelinvite.application.ChannelInviteService;
import com.voicechat.channelinvite.dto.GetInviteChannelListDto;
import com.voicechat.channelinvite.dto.InviteChannelDto;
import com.voicechat.common.constant.HeaderKey;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/")
@RequiredArgsConstructor
public class ChannelInviteController {
    private final ChannelInviteService channelInviteService;

    @PostMapping("/")
    public void inviteChannel(@RequestBody @Valid InviteChannelDto.InviteChannelReqDto inviteChannelReqDto) {
        this.channelInviteService.inviteChannel(inviteChannelReqDto);
    }

    @GetMapping("/me")
    public GetInviteChannelListDto.GetInviteChannelListResDto getInviteChannels(@RequestHeader(HeaderKey.USER_ID) final Long userId) {
        return channelInviteService.getInviteChannels(userId);
    }
}
