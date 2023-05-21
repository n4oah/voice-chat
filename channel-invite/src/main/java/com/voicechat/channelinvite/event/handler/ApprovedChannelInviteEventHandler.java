package com.voicechat.channelinvite.event.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.voicechat.channelinvite.adapter.dto.ApprovedChannelInviteDto;
import com.voicechat.channelinvite.adapter.out.ChannelInviteProducer;
import com.voicechat.domain.channelinvite.event.ApprovedChannelInviteEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ApprovedChannelInviteEventHandler {
    private final ChannelInviteProducer channelInviteProducer;

    @TransactionalEventListener(
            classes = ApprovedChannelInviteEvent.class,
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handler(ApprovedChannelInviteEvent approvedChannelInviteEvent) throws JsonProcessingException {
        channelInviteProducer.approvedChannelInvite(
            new ApprovedChannelInviteDto.ApprovedChannelInviteReqDto(approvedChannelInviteEvent.getChannelInviteId())
        );
    }
}
