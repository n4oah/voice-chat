package com.voicechat.user.exception;

import com.voicechat.common.error.ErrorCode;
import com.voicechat.common.error.exception.BusinessException;

public class ExpiredAccessTokenException extends BusinessException {
    public ExpiredAccessTokenException() {
        super(ErrorCode.EXPIRED_ACCESS_TOKEN);
    }
}
