package com.voicechat.channelinvite.adapter.channel.out;

import com.voicechat.channelinvite.adapter.channel.dto.HasUserByChannelDto;
import com.voicechat.channelinvite.adapter.channel.dto.IsChannelFullDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="channel")
public interface ChannelServiceClient {
    @GetMapping("/{channelId}/is-full")
    ResponseEntity<IsChannelFullDto.IsChannelFullResDto> isChannelFull(
            @PathVariable("channelId") Long channelId
    );

    @GetMapping("/{channelId}/has-user/{userId}")
    ResponseEntity<HasUserByChannelDto.HasUserByChannelResDto> hasUserByChannel(
            @PathVariable("channelId") Long channelId,
            @PathVariable("userId") Long userId
    );
}
