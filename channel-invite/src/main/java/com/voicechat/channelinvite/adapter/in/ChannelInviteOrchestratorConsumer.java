package com.voicechat.channelinvite.adapter.in;

import com.voicechat.channelinvite.adapter.dto.ChannelInviteTerminateEventDto;
import com.voicechat.channelinvite.application.ChannelInviteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChannelInviteOrchestratorConsumer {
    private final ChannelInviteService channelInviteService;

    @KafkaListener(topics = "channel-invite-orchestrator")
    public void channelInviteOrchestratorTerminateConsume(
            @Payload @Valid ChannelInviteTerminateEventDto.ChannelInviteTerminateEventDtoRes terminateEventDtoRes
    ) {
        switch (terminateEventDtoRes.status()) {
            case FAILED -> this.channelInviteService.rejectChannelInvite(terminateEventDtoRes.channelInviteId());
            case SUCCESS -> this.channelInviteService.successChannelInvite(terminateEventDtoRes.channelInviteId());
        }
    }
}
