package com.voicechat.channelinvite.adapter.out;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.voicechat.channelinvite.adapter.dto.ApprovedChannelInviteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChannelInviteProducerImpl implements ChannelInviteProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final String TOPIC = "channel-invite-service";

    @Override
    public void approvedChannelInvite(
            ApprovedChannelInviteDto.ApprovedChannelInviteReqDto approvedChannelInviteReqDto
    ) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(approvedChannelInviteReqDto);
        this.kafkaTemplate.send(this.TOPIC, message);
    }
}
