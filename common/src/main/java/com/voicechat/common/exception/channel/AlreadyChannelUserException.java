package com.voicechat.common.exception.channel;

import com.voicechat.common.error.ErrorCode;
import com.voicechat.common.error.exception.BusinessException;

public class AlreadyChannelUserException extends BusinessException {
    public AlreadyChannelUserException() {
        super(ErrorCode.ALREADY_USER_OF_CHANNEL);
    }
}
