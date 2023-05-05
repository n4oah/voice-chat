package com.voicechat.common.constant;

public enum ChannelMemberRole {
    MEMBER("MEMBER"),
    ASSISTANT_MANAGER("ASSISTANT_MANAGER"),
    MANAGER("MANAGER");

    private final String role;

    ChannelMemberRole(String role) {
        this.role = role;
    }
}
