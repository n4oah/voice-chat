package com.voicechat.chat.adapter.out.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.List;

public class GetChannelMemberDto {
    public record GetChannelMemberDtoRes(
        List<ChannelMemberItem> channelMembers
    ) {
        public record ChannelMemberItem(
            @NotNull
            @Positive
            Long id,

            @NotNull
            @NotEmpty
            String name,
            @NotNull
            @Positive
            Long userId
        ) {

        }
    }
}