package com.voicechat.gateway.adapter.channel.dto;

import com.voicechat.common.constant.ChannelMemberRole;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public class GetMyChannelDetailDto {
        public record GetMyChannelDetailResDto (
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