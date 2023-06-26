package com.voicechat.chat.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

public class GetChannelChatting {
    @RequiredArgsConstructor
    @Getter
    public static class GetChannelChattingReq {
        private final String nextCursorId;

        @Min(1)
        @Max(20)
        private final Integer limit = 20;
    }

    @RequiredArgsConstructor
    @Getter
    public static class GetChannelChattingRes {
        private final List<ReceiveMessage> messages;

        private final String nextCursorId;
    }
}
