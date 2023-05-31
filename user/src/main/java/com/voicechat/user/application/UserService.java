package com.voicechat.user.application;

import com.voicechat.user.dto.AuthJwtDecodeDto;
import com.voicechat.user.dto.GetUserDto;
import com.voicechat.user.dto.SignupDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void signup(SignupDto.SignupDtoReq signupDtoReq);
    boolean hasDuplicateAccount(String email);
    AuthJwtDecodeDto.AuthJwtDecodeResDto jwtDecode(String accessToken);
    GetUserDto.GetUserResDto getUserById(Long userId);
    GetUserDto.GetUserResDto getUserByEmail(String email);
}
