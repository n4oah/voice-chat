package com.voicechat.user.controller;

import com.voicechat.common.constant.HeaderKey;
import com.voicechat.user.application.UserService;
import com.voicechat.user.dto.AuthJwtDecodeDto;
import com.voicechat.user.dto.GetUserDto;
import com.voicechat.user.dto.SigninDto;
import com.voicechat.user.dto.SignupDto;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public void signup(@Valid() @RequestBody(required = true) final SignupDto.SignupDtoReq signupDtoReq) {
        this.userService.signup(signupDtoReq);
    }

    @PostMapping(
            value = "/signin",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation= SigninDto.SigninDtoRes.class)
                    )
            )
    )
    public void signin(@Valid() @RequestBody(required = true) final SigninDto.SigninDtoReq signinDtoReq) {}

    @GetMapping("/me")
    public GetUserDto.GetUserResDto getMyInfo(
        @RequestHeader(HeaderKey.USER_ID) Long userId
    ) {
        return this.userService.getUserById(userId);
    }

    @GetMapping("/by-email/{email}")
    public GetUserDto.GetUserResDto getMyInfoByEmail(
            @PathVariable String email
    ) {
        return this.userService.getUserByEmail(email);
    }

    @GetMapping("/auth/jwt/decode")
    public AuthJwtDecodeDto.AuthJwtDecodeResDto jwtDecode(
            @Valid() AuthJwtDecodeDto.AuthJwtDecodeReqDto authJwtDecodeReqDto
    ) {
        return this.userService.jwtDecode(authJwtDecodeReqDto.getAccessToken());
    }
}
