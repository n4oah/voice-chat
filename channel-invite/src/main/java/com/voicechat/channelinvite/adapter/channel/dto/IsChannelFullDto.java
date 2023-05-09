package com.voicechat.channelinvite.adapter.channel.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class IsChannelFullDto {
    public record IsChannelFullResDto(
        @NotNull
        Boolean isFull
    ) {
    }
}
