package com.voicechat.common.exception.channel;

import com.voicechat.common.error.ErrorCode;
import com.voicechat.common.error.exception.BusinessException;

public class FullChannelException extends BusinessException {
    public FullChannelException() {
        super(ErrorCode.FULL_CHANNEL);
    }
}