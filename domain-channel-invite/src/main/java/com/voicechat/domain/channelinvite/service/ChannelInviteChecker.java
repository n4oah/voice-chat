package com.voicechat.domain.channelinvite.service;

public interface ChannelInviteChecker {
    void verifyCreateChannelInvite(Long channelId, Long userId);
}