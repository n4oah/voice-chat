package com.voicechat.chat.adapter.out.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class GetUserDto {
    public record GetUserResDto(
            @NotNull
            @Positive
            Long id,
            @NotNull
            @Email
            String email,
            @NotNull
            String name
    ) {

    }
}