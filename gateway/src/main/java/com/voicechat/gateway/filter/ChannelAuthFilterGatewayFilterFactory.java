package com.voicechat.gateway.filter;

import com.voicechat.common.constant.ChannelMemberRole;
import com.voicechat.common.constant.HeaderKey;
import com.voicechat.gateway.adapter.channel.out.ChannelServiceClient;
import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.io.buffer.DataBuffer;
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
import java.util.List;
import java.util.Map;

@Component
public class ChannelAuthFilterGatewayFilterFactory
        extends AbstractGatewayFilterFactory<ChannelAuthFilterGatewayFilterFactory.Config> {
    private final ChannelServiceClient channelServiceClient;

    public ChannelAuthFilterGatewayFilterFactory(
            ChannelServiceClient channelServiceClient
    ) {
        super(Config.class);
        this.channelServiceClient = channelServiceClient;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            Map<String, String> uriVariables = ServerWebExchangeUtils.getUriTemplateVariables(exchange);

            final Long channelId = Long.parseLong(uriVariables.get("channelId"));

            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            if (!request.getHeaders().containsKey(HeaderKey.USER_ID)) {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }

            final Long userId = Long.parseLong(request.getHeaders().get(HeaderKey.USER_ID).get(0));

            return this.channelServiceClient.getMyChannel(channelId, userId)
                    .map((channel -> {
                        if (!(channel.authRoles().stream().anyMatch((role) ->
                                config.channelMemberRoles.stream().anyMatch(role2 -> role.equals(role2))))
                        ) {
                            throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
                        }

                        return exchange;
                    }))
                    .flatMap(chain::filter)
                    .onErrorResume(HttpClientErrorException.class, ((exception) -> this.convertException(
                            exchange.getResponse(),
                            exception.getStatusCode(),
                            exception.getResponseBodyAsString(StandardCharsets.UTF_8)
                    )))
                    .onErrorResume(WebClientResponseException.class, ((exception) -> this.convertException(
                            exchange.getResponse(),
                            exception.getStatusCode(),
                            exception.getResponseBodyAsString(StandardCharsets.UTF_8)
                    )));
        });
    }

    private Mono<Void> convertException(ServerHttpResponse response, HttpStatusCode httpStatus, String message) {
        response.getHeaders().add("content-type", "application/json");
        DataBuffer buffer = response.bufferFactory().wrap(message.getBytes(StandardCharsets.UTF_8));
        response.setStatusCode(httpStatus);
        return response.writeWith(Mono.just(buffer));
    }

    @Data
    public static class Config {
        private List<ChannelMemberRole> channelMemberRoles;
    }
}