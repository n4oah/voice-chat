package com.voicechat.user.dto;

import com.voicechat.domain.user.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SignupDto {
    public record SignupDtoReq(
            @NotNull
            @NotEmpty
            @Email
            String email,
            @NotEmpty
            @NotNull
            String password,
            @NotEmpty
            @NotNull
            String name
    ) {
        public User toEntity(PasswordEncoder passwordEncoder) {
            return User.createUser(email, passwordEncoder.encode(password), name);
        }
    }
}
