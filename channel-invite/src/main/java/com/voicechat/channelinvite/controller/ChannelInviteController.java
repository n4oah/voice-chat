package com.voicechat.channelinvite.controller;

import com.voicechat.channelinvite.application.ChannelInviteService;
import com.voicechat.channelinvite.dto.GetInviteChannelDetailDto;
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

    @PostMapping("/channel/{channelId}/")
    public void inviteChannel(
            @RequestBody @Valid InviteChannelDto.InviteChannelReqDto inviteChannelReqDto,
            @PathVariable("channelId") Long channelId
    ) {
        this.channelInviteService.inviteChannel(channelId, inviteChannelReqDto.email());
    }

    // 대기중인 리스트만
    @GetMapping("/channel/{channelId}/")
    public GetInviteChannelListDto.GetInviteChannelListResDto getChannelInviteMembersByChannelId(
            @PathVariable("channelId") Long channelId
    ) {
        return this.channelInviteService.getChannelInviteMembersByChannelId(channelId);
    }

    @GetMapping("/me")
    public GetInviteChannelListDto.GetInviteChannelListResDto getInviteChannels(@RequestHeader(HeaderKey.USER_ID) final Long userId) {
        return channelInviteService.getInviteChannelsByUser(userId);
    }

    @PatchMapping("/me/{channelInviteId}/approve")
    public void approveInvitedChannel(
            @RequestHeader(HeaderKey.USER_ID) final Long userId,
            @PathVariable("channelInviteId") final Long channelInviteId
    ) {
        channelInviteService.approveInvitedChannel(userId, channelInviteId);
    }

    @PatchMapping("/me/{channelInviteId}/refuse")
    public void refuseInvitedChannel(
            @RequestHeader(HeaderKey.USER_ID) final Long userId,
            @PathVariable("channelInviteId") final Long channelInviteId
    ) {
        channelInviteService.refuseInvitedChannel(userId, channelInviteId);
    }

    @GetMapping("{channelInviteId}")
    public GetInviteChannelDetailDto.GetInviteChannelDetailRes getChannelInvite(@PathVariable("channelInviteId") Long channelInviteId) {
        return channelInviteService.getInviteChannel(channelInviteId);
    }
}
