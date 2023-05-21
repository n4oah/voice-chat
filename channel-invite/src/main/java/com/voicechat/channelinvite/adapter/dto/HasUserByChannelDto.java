package com.voicechat.channelinvite.adapter.dto;

import jakarta.validation.constraints.NotNull;

public class HasUserByChannelDto {
    public record HasUserByChannelResDto(
            @NotNull
            Boolean exist
    ) {

    }
}
