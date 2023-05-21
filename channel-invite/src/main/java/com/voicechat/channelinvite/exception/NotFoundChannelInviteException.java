package com.voicechat.channelinvite.exception;

import com.voicechat.common.error.ErrorCode;
import com.voicechat.common.error.exception.BusinessException;

public class NotFoundChannelInviteException extends BusinessException {
    public NotFoundChannelInviteException() {
        super(ErrorCode.ENTITY_NOT_FOUND_CHANNEL_INVITE);
    }
}
