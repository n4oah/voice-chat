package com.voicechat.channelinvite.orchestrator.adapter.out;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.voicechat.channelinvite.orchestrator.adapter.out.dto.ChannelInviteTerminateEventDto;

public interface ChannelInviteOrchestratorServiceKafkaProducer {
    void channelInviteTerminateOrchestrator(ChannelInviteTerminateEventDto.ChannelInviteTerminateEventDtoRes failedChannelInviteDtoRes)
            throws JsonProcessingException;
}
