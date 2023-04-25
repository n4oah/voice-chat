package com.voicechat.domain.user;

public enum SocialType {
    KAKAO("KAKAO");

    private String type;

    SocialType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
