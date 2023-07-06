package com.voicechat.channel.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

public class GetChannelMemberDto {
    @RequiredArgsConstructor
    @Getter
    public static class GetChannelMemberDtoRes {
        private final List<ChannelMemberItem> channelMembers;

        @RequiredArgsConstructor
        @Getter
        public static class ChannelMemberItem {
            @NotNull
            @Positive
            private final Long id;

            @NotNull
            @NotEmpty
            private final String name;

            @NotNull
            @Positive
            private final Long userId;
        }
    }
}
