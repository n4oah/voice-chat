package com.voicechat.channelinvite.adapter;

import com.voicechat.channelinvite.adapter.channel.out.ChannelServiceClient;
import com.voicechat.domain.channelinvite.exception.AlreadyChannelUserException;
import com.voicechat.domain.channelinvite.exception.FullChannelException;
import com.voicechat.domain.channelinvite.service.ChannelInviteChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChannelInviteCheckerImpl implements ChannelInviteChecker {
    private final ChannelServiceClient channelServiceClient;


    // TODO: API 호출을 병렬적으로 처리해야 됨
    @Override
    public void verifyCreateChannelInvite(Long channelId, Long userId) {
        if (channelServiceClient.isChannelFull(channelId).getBody().isFull()) {
            throw new FullChannelException();
        }

        if (channelServiceClient.hasUserByChannel(channelId, userId).getBody().exist()) {
            throw new AlreadyChannelUserException();
        }
    }
}
