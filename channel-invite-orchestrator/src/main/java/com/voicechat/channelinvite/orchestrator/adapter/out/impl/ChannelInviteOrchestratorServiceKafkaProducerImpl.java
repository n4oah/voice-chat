package com.voicechat.channelinvite.orchestrator.adapter.out.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.voicechat.channelinvite.orchestrator.adapter.in.dto.ApprovedChannelInviteDto;
import com.voicechat.channelinvite.orchestrator.adapter.out.ChannelInviteOrchestratorServiceKafkaProducer;
import com.voicechat.channelinvite.orchestrator.adapter.out.dto.FailedChannelInviteDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChannelInviteOrchestratorServiceKafkaProducerImpl
        implements ChannelInviteOrchestratorServiceKafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final String TOPIC = "channel-invite-orchestrator";


    @Override
    public void failChannelInviteOrchestrator(FailedChannelInviteDto.FailedChannelInviteDtoRes failedChannelInviteDtoRes)
            throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(failedChannelInviteDtoRes);
        this.kafkaTemplate.send(this.TOPIC, message);
    }
}
