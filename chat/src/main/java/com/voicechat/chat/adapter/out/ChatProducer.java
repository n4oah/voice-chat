package com.voicechat.chat.adapter.out;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.voicechat.chat.dto.ChatMessage;

public interface ChatProducer {
    void sendMessage(ChatMessage chatMessage) throws JsonProcessingException;
}
