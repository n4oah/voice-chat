package com.voicechat.channelinvite.dto;

import com.voicechat.common.constant.ChannelInviteStatus;
import jakarta.validation.constraints.NotNull;

public class GetInviteChannelDetailDto {
    public record GetInviteChannelDetailRes(
        @NotNull
        Long id,
        @NotNull
        Long channelId,
        @NotNull
        Long userId,
        ChannelInviteStatus status
    ) {

    }
}
