package com.voicechat.user.application.impl;

import com.voicechat.common.exception.DuplicateUserIdException;
import com.voicechat.common.vo.UserJwtClaim;
import com.voicechat.domain.user.entity.User;
import com.voicechat.domain.user.entity.UserAuthRole;
import com.voicechat.domain.user.repository.UserAuthRoleRepository;
import com.voicechat.domain.user.repository.UserRepository;
import com.voicechat.user.application.UserService;
import com.voicechat.user.component.JwtTokenProvider;
import com.voicechat.user.dto.AuthJwtDecodeDto;
import com.voicechat.user.dto.GetUserDto;
import com.voicechat.user.dto.SignupDto;
import com.voicechat.user.exception.NotFoundUserException;
import com.voicechat.user.vo.UserVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder PasswordEncoder;
    private final UserAuthRoleRepository userAuthRoleRepository;

    @Override
    public void signup(SignupDto.SignupDtoReq signupDtoReq) {
        if (this.hasDuplicateAccount(signupDtoReq.email())) {
            throw new DuplicateUserIdException();
        }

        final User user = signupDtoReq.toEntity(this.PasswordEncoder, this.userAuthRoleRepository);

        this.userRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean hasDuplicateAccount(String email) {
        return this.userRepository.findByEmail(email).isPresent();
    }

    @Override
    public AuthJwtDecodeDto.AuthJwtDecodeResDto jwtDecode(String accessToken) {
        final UserJwtClaim account = this.jwtTokenProvider.decodeJwt(accessToken);

        return new AuthJwtDecodeDto.AuthJwtDecodeResDto(
                account.id(),
                account.email(),
                account.name()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final var user = this.userRepository.findByEmail(username).orElseThrow(NotFoundUserException::new);
        return new UserVo(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getName(),
                user.getAuthorities().stream().map(UserAuthRole::getName).collect(Collectors.toSet())
        );
    }

    @Override
    @Transactional(readOnly = true)
    public GetUserDto.GetUserResDto getUserById(Long userId) {
        final var user = this.userRepository.findById(userId).orElseThrow(NotFoundUserException::new);

        return new GetUserDto.GetUserResDto(
            user.getId(),
            user.getEmail(),
            user.getName()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public GetUserDto.GetUserResDto getUserByEmail(String email) {
        final var user = this.userRepository.findByEmail(email).orElseThrow(NotFoundUserException::new);

        return new GetUserDto.GetUserResDto(
                user.getId(),
                user.getEmail(),
                user.getName()
        );
    }
}
