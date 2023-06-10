package com.voicechat.chat.adapter.in;

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
@RequiredArgsConstructor
@Slf4j
public class ChatConsumer {
    private final SimpMessagingTemplate template;

    @KafkaListener(topics = KafkaTopic.TOPIC)
    public void chatMessageListener(
        @Payload @Valid ChatMessage chatMessage
    ) {
        System.out.println("chatMessage.content" + chatMessage.content());
        System.out.println("chatMessage" + chatMessage.channelId());
        System.out.println("chatMessage" + chatMessage.timestamp());

        this.template.convertAndSend("/topic/channel/" + chatMessage.channelId(), chatMessage);
    }
}
