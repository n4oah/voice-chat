package com.voicechat.common.event;

import lombok.Getter;

import java.time.Instant;

@Getter
public abstract class Event {
    private final Instant issuedAt;

    protected Event() {
        this.issuedAt = Instant.now();
    }
}
