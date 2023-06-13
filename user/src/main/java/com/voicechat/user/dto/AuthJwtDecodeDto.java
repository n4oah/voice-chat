package com.voicechat.user.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class AuthJwtDecodeDto {
    public record AuthJwtDecodeReqDto(@NotNull() @NotEmpty() String accessToken) {
    }

    public record AuthJwtDecodeResDto (
            @NotNull
            @NotEmpty
            Long id,
            @NotNull()
            @NotEmpty()
            String userId,
            @NotNull()
            @NotEmpty()
            String name
    ) {}
}