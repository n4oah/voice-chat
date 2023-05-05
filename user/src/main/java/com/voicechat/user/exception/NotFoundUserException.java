package com.voicechat.user.exception;

import com.voicechat.common.error.ErrorCode;
import com.voicechat.common.error.exception.BusinessException;

public class NotFoundUserException extends BusinessException {
    public NotFoundUserException() {
        super(ErrorCode.ENTITY_NOT_FOUND);
    }
}
