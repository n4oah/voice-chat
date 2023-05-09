package com.voicechat.channel.dto;

import jakarta.validation.constraints.NotNull;

public class HasUserByChannelDto {
    public record HasUserByChannelResDto(
            @NotNull
            Boolean exist
    ) {

    }
}
