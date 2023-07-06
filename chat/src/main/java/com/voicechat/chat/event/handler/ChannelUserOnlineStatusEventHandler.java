package com.voicechat.chat.event.handler;

import com.voicechat.chat.adapter.out.ChannelServiceClient;
import com.voicechat.chat.application.ChatService;
import com.voicechat.chat.dto.UserOnlineStatusEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ChannelUserOnlineStatusEventHandler {
    private final SimpMessagingTemplate template;
    private final ChannelServiceClient channelServiceClient;

    @EventListener(
            classes = UserOnlineStatusEvent.class
    )
    public void handler(UserOnlineStatusEvent userOnlineStatusEvent) {
        final var channels = this.getChannelsByUser(userOnlineStatusEvent.getUserId());

        channels.forEach((channelId) -> {
            this.template.convertAndSend("/topic/channel/" + channelId + "/online-status", userOnlineStatusEvent);
        });
    }

    private List<Long> getChannelsByUser(Long userId) {
        final var channels = this.channelServiceClient.getChannelsByUserId(userId).getBody().channels();
        return channels.stream().map((channel) -> channel.id()).toList();
    }
}
