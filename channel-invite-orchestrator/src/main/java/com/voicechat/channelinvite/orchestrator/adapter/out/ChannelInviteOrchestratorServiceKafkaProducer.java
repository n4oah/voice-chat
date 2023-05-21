package com.voicechat.channelinvite.orchestrator.adapter.out;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.voicechat.channelinvite.orchestrator.adapter.out.dto.FailedChannelInviteDto;

public interface ChannelInviteOrchestratorServiceKafkaProducer {
    void failChannelInviteOrchestrator(FailedChannelInviteDto.FailedChannelInviteDtoRes failedChannelInviteDtoRes) throws JsonProcessingException;
}
