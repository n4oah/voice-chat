package com.voicechat.chat.dto;

import com.voicechat.chat.constant.UserOnlineStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserOnlineStatusEvent {
    @NotNull
    @Positive
    private final Long userId;
    @NotNull
    private final UserOnlineStatus userOnlineStatus;
}
