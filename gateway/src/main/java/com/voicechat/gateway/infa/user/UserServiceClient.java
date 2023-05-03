package com.voicechat.gateway.infa.user;

import com.voicechat.common.vo.UserJwtClaim;
import com.voicechat.gateway.infa.user.dto.UserAuthJwtDecodeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserServiceClient {
    private final WebClient.Builder userServiceWebClient;

    public Mono<UserJwtClaim> authJwtDecodeToUser(
            UserAuthJwtDecodeDto.UserAuthJwtDecodeReqDto authJwtDecodeReqDto
    ) {
        return userServiceWebClient
                .build()
                .get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/auth/jwt/decode")
                                .queryParam("accessToken", authJwtDecodeReqDto.accessToken()).build())
                .retrieve()
                .bodyToMono(UserJwtClaim.class);
    }
}
