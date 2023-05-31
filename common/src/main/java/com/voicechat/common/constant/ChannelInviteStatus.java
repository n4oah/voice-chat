package com.voicechat.common.constant;

public enum ChannelInviteStatus {
    // 대기중
    WAITED("WAITED"),
    // 수락했음
    ACCEPTED("ACCEPTED"),
    // 거절
    REFUSED("REFUSED"),
    // 에러떠서 자동 거절
    REJECTED("REJECTED"),
    // 수락 후 최종 채널 맴버에 추가 됨
    SUCCEED("SUCCEED");

    private String type;

    ChannelInviteStatus(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
