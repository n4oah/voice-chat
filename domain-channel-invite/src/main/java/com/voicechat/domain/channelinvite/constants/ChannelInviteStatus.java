package com.voicechat.domain.channelinvite.constants;

public enum ChannelInviteStatus {
    WAITED("WAITED"),
    ACCEPTED("ACCEPTED"),
    REFUSED("REFUSED"),
    REJECTED("REJECTED");

    private String type;

    ChannelInviteStatus(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
