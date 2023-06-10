package com.voicechat.chat.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UUID;

public record ChatMessage(
        @NotNull
        @UUID
        String uuid,
        @NotNull
        Long senderUserId,
        @NotNull
        Long channelId,
        @NotNull
        @NotEmpty
        String content,
        @NotNull
        @NotEmpty
        String timestamp
) {
}
