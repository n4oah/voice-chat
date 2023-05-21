package com.voicechat.channelinvite.adapter.out;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.voicechat.channelinvite.adapter.dto.ApprovedChannelInviteDto;

public interface ChannelInviteProducer {
    void approvedChannelInvite(ApprovedChannelInviteDto.ApprovedChannelInviteReqDto approvedChannelInviteReqDto) throws JsonProcessingException;
}
