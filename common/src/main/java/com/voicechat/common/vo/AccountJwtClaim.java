package com.voicechat.common.vo;

public record AccountJwtClaim(Long id, String email, String name) implements IJwtClaim {}