package com.voicechat.chat.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UUID;

public record ReceiveMessage(
    @NotNull
    @NotEmpty
    String id,
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
    String createdAt,
    @NotNull
    @NotEmpty
    String senderUserName
) {
}
