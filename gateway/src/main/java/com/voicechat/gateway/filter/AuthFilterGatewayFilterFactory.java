package com.voicechat.gateway.filter;

import com.voicechat.gateway.infa.user.UserServiceClient;
import com.voicechat.gateway.infa.user.dto.UserAuthJwtDecodeDto;
import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class AuthFilterGatewayFilterFactory
        extends AbstractGatewayFilterFactory<AuthFilterGatewayFilterFactory.Config> {
    private final UserServiceClient userServiceClient;

    private final String USER_ID_HEADER_KEY = "X-auth-user-id";

    public AuthFilterGatewayFilterFactory(UserServiceClient userServiceClient) {
        super(Config.class);
        this.userServiceClient = userServiceClient;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            System.out.println("Hi");
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }

            final String authorization = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

            if (!hasAccessToken(authorization)) {
                return this.convertException(
                        exchange.getResponse(),
                        HttpStatus.UNAUTHORIZED,
                        HttpStatus.UNAUTHORIZED.getReasonPhrase()
                );
            }

            final String accessToken = authorization.replace("Bearer ", "");

            return this.userServiceClient.authJwtDecodeToUser(new UserAuthJwtDecodeDto.UserAuthJwtDecodeReqDto(accessToken))
//                            .flatMap((accountAuthJwtDecodeResDto) -> Mono.just(accountAuthJwtDecodeResDto))
                            .map((accountAuthJwtDecodeResDto -> {
                                exchange.getRequest()
                                        .mutate()
                                        .header(this.USER_ID_HEADER_KEY, String.valueOf(accountAuthJwtDecodeResDto.id()));
                                return exchange;
                            }))
                            .flatMap(chain::filter)
                            .onErrorResume(HttpClientErrorException.class, ((exception) -> this.convertException(
                                    exchange.getResponse(),
                                    exception.getStatusCode(),
                                    exception.getResponseBodyAsString(StandardCharsets.UTF_8)
                            )
                            ))
                            .onErrorResume(WebClientResponseException.class, ((exception) -> this.convertException(
                                    exchange.getResponse(),
                                    exception.getStatusCode(),
                                    exception.getResponseBodyAsString(StandardCharsets.UTF_8)
                            )
                        ));
        });
    }

    private Mono<Void> convertException(ServerHttpResponse response, HttpStatusCode httpStatus, String message) {
        response.getHeaders().add("content-type", "application/json");
        DataBuffer buffer = response.bufferFactory().wrap(message.getBytes(StandardCharsets.UTF_8));
        response.setStatusCode(httpStatus);
        return response.writeWith(Mono.just(buffer));
    }

    private boolean hasAccessToken(String authorization) {
        if (authorization == null || !StringUtils.hasText(authorization) || !authorization.startsWith("Bearer")) {
            return false;
        }

        return true;
    }

    @Data
    public static class Config {
    }
}