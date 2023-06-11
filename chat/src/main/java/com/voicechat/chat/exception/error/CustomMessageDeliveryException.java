package com.voicechat.chat.exception.error;

import com.voicechat.common.error.ErrorCode;
import org.springframework.messaging.MessageDeliveryException;

public class CustomMessageDeliveryException extends MessageDeliveryException {
    private final ErrorCode errorCode;

    public CustomMessageDeliveryException(ErrorCode errorCode) {
        super(errorCode.getCode());

        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }
}
