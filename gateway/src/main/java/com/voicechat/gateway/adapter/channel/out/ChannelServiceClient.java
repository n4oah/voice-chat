package com.voicechat.gateway.adapter.channel.out;

import com.voicechat.common.constant.HeaderKey;
import com.voicechat.gateway.adapter.channel.dto.GetMyChannelDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ChannelServiceClient {
    @Qualifier("channelServiceWebClient")
    private final WebClient.Builder channelServiceWebClient;

    public Mono<GetMyChannelDetailDto.GetMyChannelDetailResDto> getMyChannel(
            Long channelId, Long userId
    ) {
        return channelServiceWebClient
                .build()
                .get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/me/" + channelId)
                        .build())
                .header(HeaderKey.USER_ID, userId.toString())
                .retrieve()
                .bodyToMono(GetMyChannelDetailDto.GetMyChannelDetailResDto.class);
    }
}
