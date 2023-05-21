package com.voicechat.channelinvite.orchestrator.adapter.out.impl;

import com.voicechat.channelinvite.orchestrator.adapter.out.ChannelServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ChannelServiceClientImpl implements ChannelServiceClient {
    private final WebClient.Builder channelWebClient;

    @Override
    public Mono addChannelMember(Long channelId, Long userId) {
        return this.channelWebClient
                .build()
                .post()
                .uri(uriBuilder ->
                        uriBuilder
                                .path(channelId + "/channel-member/" + userId)
                                .build()
                )
                .retrieve()
                .bodyToMono(Void.class);
    }

    @Override
    public Mono removeChannelMember(Long channelId, Long userId) {
        return this.channelWebClient
                .build()
                .delete()
                .uri(uriBuilder ->
                        uriBuilder
                                .path(channelId + "/channel-member/" + userId)
                                .build()
                )
                .retrieve()
                .bodyToMono(Void.class);
    }
}
