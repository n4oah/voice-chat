package com.voicechat.chat.adapter.out.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class UserAuthJwtDecodeDto {

    // record 쓰면 feign SpringQueryMap 안 먹어서 static class 로
    @RequiredArgsConstructor
    @Getter
    public static class UserAuthJwtDecodeReqDto {
        @NotNull() @NotEmpty()
        private final String accessToken;
    }
}
