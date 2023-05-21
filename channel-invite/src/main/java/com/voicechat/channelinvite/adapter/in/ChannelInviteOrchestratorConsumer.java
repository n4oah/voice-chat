package com.voicechat.channelinvite.adapter.in;

import com.voicechat.channelinvite.adapter.dto.FailedChannelInviteDto;
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
    public void rejectChannelInviteConsume(
            @Payload @Valid FailedChannelInviteDto.FailedChannelInviteDtoRes failedChannelInviteDtoRes
    ) {
        this.channelInviteService.rejectChannelInvite(failedChannelInviteDtoRes.channelInviteId());
    }
}
