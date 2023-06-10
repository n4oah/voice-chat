package com.voicechat.chat.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class SendMessageDto {
    public record SendMessageReqDto(
        @NotNull
        @NotEmpty
        String content
    ) {
    }

    public record SendMessageResDto(
        String uuid
    ) {
    }
}
