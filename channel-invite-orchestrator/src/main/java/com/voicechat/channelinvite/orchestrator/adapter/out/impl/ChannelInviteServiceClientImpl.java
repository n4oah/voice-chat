package com.voicechat.channelinvite.orchestrator.adapter.out.impl;

import com.voicechat.channelinvite.orchestrator.adapter.out.ChannelInviteServiceClient;
import com.voicechat.channelinvite.orchestrator.adapter.out.dto.GetInviteChannelDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ChannelInviteServiceClientImpl implements ChannelInviteServiceClient {
    private final WebClient.Builder channelInviteWebClient;

    @Override
    public Mono<GetInviteChannelDetailDto.GetInviteChannelDetailRes> getChannelDetail(Long channelInviteId) {
        return this.channelInviteWebClient
                .build()
                .get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/" + channelInviteId)
                                .build()
                )
                .retrieve()
                .bodyToMono(GetInviteChannelDetailDto.GetInviteChannelDetailRes.class);
    }
}
