package com.voicechat.chat.adapter.in;

import com.voicechat.chat.application.SendChatService;
import com.voicechat.chat.constant.KafkaTopic;
import com.voicechat.chat.dto.ChatMessage;
import com.voicechat.chat.dto.ReceiveMessage;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
        final var receiveMessage = chatService.sendChannelMessage(chatMessage);

        this.template.convertAndSend("/topic/channel/" + chatMessage.channelId(), receiveMessage);
    }
}
