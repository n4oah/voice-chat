package com.voicechat.channel.dto;

import java.util.List;

public class GetChannelListDto {
    public record GetChannelListResDto(
        List<GetChannelDetailDto.GetMyChannelDetailResDto> channels
    ) {

    }
}
