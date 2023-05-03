package com.voicechat.common.exception;

import com.voicechat.common.error.ErrorCode;
import com.voicechat.common.error.exception.BusinessException;

public class DuplicateUserIdException extends BusinessException {
    public DuplicateUserIdException() {
        super(ErrorCode.EMAIL_DUPLICATION);
    }
}
