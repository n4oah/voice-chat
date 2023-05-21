package com.voicechat.channelinvite.orchestrator.adapter.out.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class FailedChannelInviteDto {
    public record FailedChannelInviteDtoRes(
            @NotNull
            @Positive
            Long channelInviteId
    ) {

    }
}
