package com.voicechat.channel.dto;

import jakarta.validation.constraints.*;

public class CreateChannelDto {
    public record CreateChannelDtoReq(
            @NotNull
            @NotBlank
            String name,
            @NotNull
            @Min(1)
            @Max(10)
            Integer maxNumberOfMember
    ) {

    }
}
