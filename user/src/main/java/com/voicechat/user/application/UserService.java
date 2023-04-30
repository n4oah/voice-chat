package com.voicechat.user.application;

import com.voicechat.common.vo.AccountJwtClaim;
import com.voicechat.domain.user.entity.User;
import com.voicechat.domain.user.repository.UserRepository;
import com.voicechat.user.component.JwtTokenProvider;
import com.voicechat.user.dto.AuthJwtDecodeDto;
import com.voicechat.user.dto.SignupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder PasswordEncoder;

    public void signup(SignupDto.SignupDtoReq signupDtoReq) {
        if (this.hasDuplicateAccount(signupDtoReq.email())) {
//            throw new DuplicateUserIdException(signupDtoReq.email());
            return;
        }

        final User user = signupDtoReq.toEntity(this.PasswordEncoder);

        this.userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public boolean hasDuplicateAccount(String email) {
        return this.userRepository.findByEmail(email).isPresent();
    }

    public AuthJwtDecodeDto.AuthJwtDecodeResDto jwtDecode(String accessToken) {
        final AccountJwtClaim account = this.jwtTokenProvider.decodeJwt(accessToken);

        return new AuthJwtDecodeDto.AuthJwtDecodeResDto(
                account.id(),
                account.email(),
                account.name()
        );
    }
}
