package com.voicechat.common.error;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Optional;

public enum ErrorCode {

    // Common
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C001", "INTERNAL_SERVER_ERROR"),
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "C002", "Entity Not Found"),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "C003", "Invalid Validation"),
    UNAUTHORIZED_ERROR(HttpStatus.UNAUTHORIZED, "C004", "Unauthorized"),

    // Member
    EMAIL_DUPLICATION(HttpStatus.CONFLICT, "M001", "Email is Duplication"),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "M002", "Expired Access Token"),

    // Channel
    FULL_CHANNEL(HttpStatus.CONFLICT, "CH001", "The channel is full"),
    ALREADY_USER_OF_CHANNEL(HttpStatus.CONFLICT, "CH002", "The user is already a member of the channel"),
    ALREADY_CHANNEL_INVITED_USER(HttpStatus.CONFLICT, "CH003", "The user is already a invited of the channel"),

    // Channel Invite
    ENTITY_NOT_FOUND_CHANNEL_INVITE(HttpStatus.CONFLICT, "CH_IV_001", "Entity Not Found")
    ;

    private final String code;
    private final String message;
    private HttpStatus status;

    ErrorCode(final HttpStatus status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public static Optional<ErrorCode> getErrorCodeByCode(String code) {
        return Arrays.stream(ErrorCode.values()).filter((ec) -> ec.code.equals(code)).findAny();
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }
}