package com.voicechat.common.event;

import org.springframework.context.ApplicationEventPublisher;

public class Events {
    public static ApplicationEventPublisher publisher;

    public static void setPublisher(ApplicationEventPublisher publisher) {
        Events.publisher = publisher;
    }

    public static void raise(Event event) {
        if (publisher != null) {
            publisher.publishEvent(event);
        }
    }
}
