package com.voicechat.channelinvite.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class InviteChannelDto {
    public record InviteChannelReqDto(
            @NotNull
            @Positive
            Long userId
    ) {

    }
}
