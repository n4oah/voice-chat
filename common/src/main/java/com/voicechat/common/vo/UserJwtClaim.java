package com.voicechat.common.vo;

public record UserJwtClaim(Long id, String email, String name) implements IJwtClaim {}