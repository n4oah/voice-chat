package com.voicechat.channel.dto;

import jakarta.validation.constraints.NotNull;

public class IsFullChannelDto {
    public record IsFullChannelResDto(
            @NotNull
            Boolean isFull
    ) {

    }
}
