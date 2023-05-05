package com.voicechat.user.dto;

import com.voicechat.domain.user.entity.User;
import com.voicechat.domain.user.entity.UserAuthRole;
import com.voicechat.domain.user.repository.UserAuthRoleRepository;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

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
        public User toEntity(PasswordEncoder passwordEncoder, UserAuthRoleRepository userAuthRoleRepository) {
            return User.createUser(
                    email,
                    passwordEncoder.encode(password),
                    name,
                    userAuthRoleRepository
            );
        }
    }
}
