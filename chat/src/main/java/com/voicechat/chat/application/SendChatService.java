package com.voicechat.chat.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.voicechat.chat.adapter.out.ChatProducer;
import com.voicechat.chat.dto.ChatMessage;
import com.voicechat.chat.dto.GetChannelChatting;
import com.voicechat.chat.dto.ReceiveMessage;
import com.voicechat.chat.dto.SendMessageDto;
import com.voicechat.domain.chat.entity.ChannelChat;
import com.voicechat.domain.chat.entity.QChannelChat;
import com.voicechat.domain.chat.repository.ChannelChatRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QSort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
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

    public ReceiveMessage sendChannelMessage(ChatMessage chatMessage) {
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

        return this.ofReceiveMessage(channelChat);
    }

    public GetChannelChatting.GetChannelChattingRes getChannelChattingList(
        Long channelId,
        GetChannelChatting.GetChannelChattingReq getChannelChattingReq
    ) {
        final var pageRequest = PageRequest.of(
                0,
                getChannelChattingReq.getLimit(),
                new QSort(QChannelChat.channelChat.id.desc())
        );

        final var messages =
                (getChannelChattingReq.getNextCursorId() != null ?
                    this.channelChatRepository.findByChannelId(
                        channelId,
                        getChannelChattingReq.getNextCursorId(),
                        pageRequest
                    ) : this.channelChatRepository.findByChannelId(
                        channelId,
                        pageRequest
                    ))
        .stream().map((chat) -> this.ofReceiveMessage(chat)).toList();

        if (messages.size() == 0) {
            return new GetChannelChatting.GetChannelChattingRes(Collections.emptyList(), null);
        }

        return new GetChannelChatting.GetChannelChattingRes(
            messages,
            messages.get(messages.size() - 1).id()
        );
    }

    private ReceiveMessage ofReceiveMessage(ChannelChat channelChat) {
        return new ReceiveMessage(
                channelChat.getId().toString(),
                channelChat.getUuid(),
                channelChat.getSenderUserId(),
                channelChat.getChannelId(),
                channelChat.getContent(),
                channelChat.getCreatedDate().atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern(
                        SENT_MESSAGE_FORMAT_PATTERN
                ))
        );
    }
}
