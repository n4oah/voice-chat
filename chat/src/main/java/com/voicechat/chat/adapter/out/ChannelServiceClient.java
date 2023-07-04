package com.voicechat.chat.adapter.out;

import com.voicechat.chat.adapter.out.dto.GetChannelListDto;
import com.voicechat.chat.adapter.out.dto.GetChannelMemberDto;
import com.voicechat.common.constant.HeaderKey;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="channel")
public interface ChannelServiceClient {
    @GetMapping("{channelId}/channel-member/")
    ResponseEntity<GetChannelMemberDto.GetChannelMemberDtoRes> getChannelMembers(
            @PathVariable("channelId") Long channelId
    );

    @GetMapping("me")
    ResponseEntity<GetChannelListDto.GetChannelListResDto> getChannelsByUserId(
            @RequestHeader(HeaderKey.USER_ID) Long userId
    );
}
