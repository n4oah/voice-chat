package com.voicechat.chat.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.voicechat.chat.adapter.out.ChatProducer;
import com.voicechat.chat.dto.ChatMessage;
import com.voicechat.chat.dto.SendMessageDto;
import com.voicechat.domain.chat.entity.ChannelChat;
import com.voicechat.domain.chat.repository.ChannelChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SendChatService {
    private final ChatProducer chatProducer;
    private final ChannelChatRepository channelChatRepository;
    private final static String SENT_MESSAGE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

    public UUID sendMessageEvent(
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
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(
                        SENT_MESSAGE_FORMAT_PATTERN
                ))
        ));

        return uuid;
    }

    public ChannelChat sendChannelMessage(ChatMessage chatMessage) {
        final var channelChat = ChannelChat.sendMessage(
                chatMessage.uuid(),
                chatMessage.senderUserId(),
                chatMessage.channelId(),
                chatMessage.content(),
                LocalDateTime
                        .parse(chatMessage.timestamp(),
                                DateTimeFormatter.ofPattern(SENT_MESSAGE_FORMAT_PATTERN))
                        .toInstant(ZoneOffset.UTC)
        );

        this.channelChatRepository.save(channelChat);

        return channelChat;
    }
}
