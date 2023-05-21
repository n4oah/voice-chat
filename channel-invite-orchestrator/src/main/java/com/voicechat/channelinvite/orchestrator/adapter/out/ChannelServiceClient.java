package com.voicechat.channelinvite.orchestrator.adapter.out;

import reactor.core.publisher.Mono;

public interface ChannelServiceClient {
    Mono addChannelMember(Long channelId, Long userId);
    Mono removeChannelMember(Long channelId, Long userId);
}
