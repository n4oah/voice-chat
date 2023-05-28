package com.voicechat.channel.controller;

import com.voicechat.channel.application.ChannelService;
import com.voicechat.channel.dto.*;
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

    @GetMapping("/{channelId}")
    public GetChannelDetailDto.GetChannelDetailResDto getChannelDetail(
            @PathVariable("channelId") Long channelId
    ) {
        return this.channelService.getChannelDetail(channelId);
    }

    @GetMapping("/{channelId}/is-full")
    public IsFullChannelDto.IsFullChannelResDto isFullChannel(
            @PathVariable("channelId") Long channelId
    ) {
        return new IsFullChannelDto.IsFullChannelResDto(this.channelService.isFullChannel(channelId));
    }

    @GetMapping("/{channelId}/has-user/{userId}")
    public HasUserByChannelDto.HasUserByChannelResDto hasUserByChannel(
            @PathVariable("channelId") Long channelId,
            @PathVariable("userId") Long userId
    ) {
        return new HasUserByChannelDto.HasUserByChannelResDto(this.channelService.hasUserByChannel(channelId, userId));
    }

    @PostMapping("{channelId}/channel-member/{userId}")
    public void addChannelMember(
        @PathVariable("channelId") Long channelId,
        @PathVariable("userId") Long userId
    ) {
        this.channelService.addChannelMember(channelId, userId);
    }

    @DeleteMapping("{channelId}/channel-member/{userId}")
    public void removeChannelMember(
            @PathVariable("channelId") Long channelId,
            @PathVariable("userId") Long userId
    ) {
        this.channelService.removeChannelMember(channelId, userId);
    }

    @GetMapping("/me")
    public GetChannelListDto.GetChannelListResDto getMyChannels(
        @RequestHeader(HeaderKey.USER_ID) final Long userId
    ) {
        return this.channelService.getChannelsByUserId(userId);
    }

    @GetMapping("/me/{channelId}")
    public GetChannelDetailDto.GetMyChannelDetailResDto getMyChannel(
            @RequestHeader(HeaderKey.USER_ID) final Long userId,
            @PathVariable("channelId") final Long channelId
    ) {
        return this.channelService.getChannelByUserId(channelId, userId);
    }
}
