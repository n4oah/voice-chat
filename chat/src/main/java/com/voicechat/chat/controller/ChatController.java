package com.voicechat.chat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.voicechat.chat.application.ChatService;
import com.voicechat.chat.dto.ChannelVoiceChatPeerDto;
import com.voicechat.chat.dto.GetChannelChatting;
import com.voicechat.chat.dto.GetChannelOnlineUsersDto;
import com.voicechat.chat.dto.SendMessageDto;
import com.voicechat.common.constant.HeaderKey;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/channel/{channelId}/")
    public SendMessageDto.SendMessageResDto sendMessage(
            @RequestBody @Valid SendMessageDto.SendMessageReqDto sendMessageReqDto,
            @RequestHeader(HeaderKey.USER_ID) Long userId,
            @PathVariable("channelId") Long channelId
    ) throws JsonProcessingException {
        final var uuid = this.chatService.sendMessageEvent(userId, channelId, sendMessageReqDto);

        return new SendMessageDto.SendMessageResDto(uuid.toString());
    }

    @GetMapping("/channel/{channelId}/")
    public GetChannelChatting.GetChannelChattingRes getChannelChattingList(
            @PathVariable("channelId") Long channelId,
            GetChannelChatting.GetChannelChattingReq getChannelChattingReq
    ) {
        return this.chatService.getChannelChattingList(channelId, getChannelChattingReq);
    }

    @GetMapping("/channel/{channelId}/online-users/")
    public GetChannelOnlineUsersDto.GetChannelOnlineUsersResDto getChannelOnlineUsers(
            @PathVariable("channelId") Long channelId
    ) {
        return new GetChannelOnlineUsersDto.GetChannelOnlineUsersResDto(
                this.chatService.getChannelOnlineUsers(channelId)
        );
    }

    @MessageMapping("/channel/voice/{channelId}/peer")
    public void voiceChannelPeer(
        SimpMessageHeaderAccessor headerAccessor,
        @DestinationVariable("channelId") Long channelId,
        @Header("simpSessionId") String sessionId
    ) {
        System.out.println(headerAccessor.getUser().getName());
        System.out.println("channelId" + channelId);
        System.out.println("sessionId" + sessionId);

        this.simpMessagingTemplate.convertAndSend(
                "/topic/channel/voice/" + channelId + "/peer",
                new ChannelVoiceChatPeerDto(
                    Long.parseLong(headerAccessor.getUser().getName()),
                    sessionId
                )
        );
    }
}
