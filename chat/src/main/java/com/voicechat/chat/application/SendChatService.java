package com.voicechat.chat.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.voicechat.chat.adapter.out.ChatProducer;
import com.voicechat.chat.dto.ChatMessage;
import com.voicechat.chat.dto.SendMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SendChatService {
    private final ChatProducer chatProducer;

    public UUID sendMessage(
            Long userId,
            Long channelId,
            SendMessageDto.SendMessageReqDto sendMessageReqDto
    ) throws JsonProcessingException {
        final var uuid = UUID.randomUUID();

        chatProducer.sendMessage(new ChatMessage(
                uuid.toString(),
                userId,
                channelId,
                sendMessageReqDto.content(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))
        ));

        return uuid;
    }
}
