package com.voicechat.chat.adapter.in;

import com.voicechat.chat.application.ChatService;
import com.voicechat.chat.constant.KafkaTopic;
import com.voicechat.chat.dto.ChatMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ChatConsumer {
    private final ChatService chatService;
    private final SimpMessagingTemplate simpMessagingTemplate;


    @KafkaListener(topics = KafkaTopic.TOPIC)
    public void chatMessageListener(
        @Payload @Valid ChatMessage chatMessage
    ) {
        final var receiveMessage = chatService.sendChannelMessage(chatMessage);

        this.simpMessagingTemplate.convertAndSend("/topic/channel/" + chatMessage.channelId(), receiveMessage);
    }
}
