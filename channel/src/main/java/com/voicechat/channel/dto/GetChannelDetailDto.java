package com.voicechat.channel.dto;

import com.voicechat.common.constant.ChannelMemberRole;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

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

    public record GetMyChannelDetailResDto(
            @NotNull
            @Positive
            Long id,
            @NotNull
            String name,
            @NotNull
            Integer maxNumberOfMember,
            @NotNull
            List<ChannelMemberRole> authRoles
    ) {
    }
}
