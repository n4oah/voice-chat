package com.voicechat.channelinvite.adapter.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ChannelInviteTerminateEventDto {
    public record ChannelInviteTerminateEventDtoRes(
            @NotNull
            @Positive
            Long channelInviteId,
            @NotNull
            ChannelInviteTerminateEventStatus status
    ) {
    }

    public enum ChannelInviteTerminateEventStatus {
        SUCCESS("SUCCESS"),
        FAILED("FAILED");

        private String type;

        ChannelInviteTerminateEventStatus(String type) {
            this.type = type;
        }

        public String getType() {
            return this.type;
        }
    }
}

