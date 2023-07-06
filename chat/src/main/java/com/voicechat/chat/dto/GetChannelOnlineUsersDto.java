package com.voicechat.chat.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

public class GetChannelOnlineUsersDto {
    @RequiredArgsConstructor
    @Getter
    public static class GetChannelOnlineUsersResDto {
        @Positive
        @NotNull
        private final List<Long> userIds;
    }
}
