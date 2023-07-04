package com.voicechat.chat.handler;

import com.voicechat.chat.dto.UserOnlineStatusEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApprovedChannelInviteEventHandler {
    private final SimpMessagingTemplate template;

    @EventListener(
            classes = UserOnlineStatusEvent.class
    )
    public void handler(UserOnlineStatusEvent userOnlineStatusEvent) {
        userOnlineStatusEvent.getChannelIds().forEach((channelId) -> {
            this.template.convertAndSend("/topic/channel/" + channelId + "/online-status", userOnlineStatusEvent);
        });
    }
}
