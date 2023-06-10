package com.voicechat.chat.adapter.out.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.voicechat.chat.adapter.out.ChatProducer;
import com.voicechat.chat.constant.KafkaTopic;
import com.voicechat.chat.dto.ChatMessage;
import com.voicechat.chat.dto.SendMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatProducerImpl implements ChatProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void sendMessage(ChatMessage chatMessage) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(chatMessage);
        this.kafkaTemplate.send(KafkaTopic.TOPIC, message);
    }
}
