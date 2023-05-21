package com.voicechat.channelinvite.orchestrator.dto;

import com.voicechat.common.saga.WorkflowStepStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class AddChannelMemberDto {
    public record AddChannelMemberReqDto(
        @NotNull
        @Positive
        Long channelInviteId,
        @NotNull
        @Positive
        Long channelId,
        @NotNull
        @Positive
        Long userId
    ) {
    }

    public record AddChannelMemberResDto(
            @NotNull
            @Positive
            Long channelInviteId,
            @NotNull
            @Positive
            Long channelId,
            @NotNull
            @Positive
            Long userId,
            @NotNull
            WorkflowStepStatus workflowStatus
    ) {
    }
}
