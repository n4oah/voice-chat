package com.voicechat.chat.dto;

import com.voicechat.chat.constant.UserOnlineStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class UserOnlineStatusEvent {
    @NotNull
    @Positive
    private final Long userId;
    private final List<Long> channelIds;
    @NotNull
    private final UserOnlineStatus userOnlineStatus;
}
