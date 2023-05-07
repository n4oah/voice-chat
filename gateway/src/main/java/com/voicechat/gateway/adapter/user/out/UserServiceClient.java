package com.voicechat.gateway.adapter.user.out;

import com.voicechat.common.vo.UserJwtClaim;
import com.voicechat.gateway.adapter.user.dto.UserAuthJwtDecodeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserServiceClient {
    @Qualifier("userServiceWebClient")
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
