package com.voicechat.user.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class AuthJwtDecodeDto {
    @RequiredArgsConstructor
    @Getter()
    public static class AuthJwtDecodeReqDto {
        @NotNull() @NotEmpty()
        private final String accessToken;
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