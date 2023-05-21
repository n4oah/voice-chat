package com.voicechat.channelinvite.orchestrator.adapter.out;

import com.voicechat.channelinvite.orchestrator.adapter.out.dto.GetInviteChannelDetailDto;
import reactor.core.publisher.Mono;

public interface ChannelInviteServiceClient {
    Mono<GetInviteChannelDetailDto.GetInviteChannelDetailRes>
        getChannelDetail(Long channelInviteId);
}
