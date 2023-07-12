package com.voicechat.chat.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ChannelVoiceChatPeerDto(
        @NotNull
        @Positive
        Long userId,
        @NotNull
        @NotNull
        String peerId
) {

}
