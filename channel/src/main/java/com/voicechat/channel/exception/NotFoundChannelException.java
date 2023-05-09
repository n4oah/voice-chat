package com.voicechat.channel.exception;

import com.voicechat.common.error.ErrorCode;
import com.voicechat.common.error.exception.BusinessException;

public class NotFoundChannelException extends BusinessException {
    public NotFoundChannelException() {
        super(ErrorCode.ENTITY_NOT_FOUND);
    }
}
