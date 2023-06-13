package com.voicechat.chat.adapter.in;

import com.voicechat.chat.application.SendChatService;
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
    private final SendChatService chatService;
    private final SimpMessagingTemplate template;


    @KafkaListener(topics = KafkaTopic.TOPIC)
    public void chatMessageListener(
        @Payload @Valid ChatMessage chatMessage
    ) {
        System.out.println("AA" + chatService.sendChannelMessage(chatMessage));

        this.template.convertAndSend("/topic/channel/" + chatMessage.channelId(), chatMessage);
    }
}
