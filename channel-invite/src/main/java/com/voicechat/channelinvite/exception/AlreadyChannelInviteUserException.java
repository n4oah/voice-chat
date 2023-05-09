package com.voicechat.channelinvite.exception;

import com.voicechat.common.error.ErrorCode;
import com.voicechat.common.error.exception.BusinessException;

public class AlreadyChannelInviteUserException extends BusinessException {
    public AlreadyChannelInviteUserException() {
        super(ErrorCode.ALREADY_CHANNEL_INVITED_USER);
    }
}
