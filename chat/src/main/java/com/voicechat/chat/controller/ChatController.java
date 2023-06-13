package com.voicechat.chat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.voicechat.chat.application.SendChatService;
import com.voicechat.chat.dto.SendMessageDto;
import com.voicechat.common.constant.HeaderKey;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class ChatController {
    private final SendChatService chatService;

    @PostMapping("/channel/{channelId}/")
    public SendMessageDto.SendMessageResDto sendMessage(
            @RequestBody @Valid SendMessageDto.SendMessageReqDto sendMessageReqDto,
            @RequestHeader(HeaderKey.USER_ID) Long userId,
            @PathVariable("channelId") Long channelId
    ) throws JsonProcessingException {
        final var uuid = this.chatService.sendMessageEvent(userId, channelId, sendMessageReqDto);

        return new SendMessageDto.SendMessageResDto(uuid.toString());
    }
}
