package com.voicechat.channelinvite.adapter.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class GetChannelDetailDto {
    public record GetChannelDetailResDto(
            @NotNull
            @Positive
            Long id,
            @NotNull
            String name,
            @NotNull
            Integer maxNumberOfMember
    ) {
    }
}
