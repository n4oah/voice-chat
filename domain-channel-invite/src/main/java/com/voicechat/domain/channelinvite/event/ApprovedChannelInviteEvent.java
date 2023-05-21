package com.voicechat.domain.channelinvite.event;

import com.voicechat.common.event.Event;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ApprovedChannelInviteEvent extends Event {
    @NotNull
    private final Long channelInviteId;
}
