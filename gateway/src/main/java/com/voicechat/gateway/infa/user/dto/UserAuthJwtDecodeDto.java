package com.voicechat.gateway.infa.user.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class UserAuthJwtDecodeDto {
    public record UserAuthJwtDecodeReqDto(@NotNull() @NotEmpty() String accessToken) {
    }
}
