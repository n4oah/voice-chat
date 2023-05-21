package com.voicechat.channelinvite.orchestrator.adapter.in.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ApprovedChannelInviteDto {
    public record ApprovedChannelInviteReqDto(
            @NotNull
            @Positive
            Long channelInviteId
    ) {
    }
}
