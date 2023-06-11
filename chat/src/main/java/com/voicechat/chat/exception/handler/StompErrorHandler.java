package com.voicechat.chat.exception.handler;

import com.voicechat.chat.exception.error.CustomMessageDeliveryException;
import com.voicechat.common.error.ErrorCode;
import com.voicechat.common.error.exception.BusinessException;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

import java.nio.charset.StandardCharsets;

@Component
public class StompErrorHandler extends StompSubProtocolErrorHandler {
    @Override
    public Message<byte[]> handleClientMessageProcessingError(Message<byte[]>clientMessage, Throwable ex) {
        System.out.println("ZXC");
        System.out.println("ex" + ex);
        if (ex instanceof CustomMessageDeliveryException) {
            System.out.println("ASD!@");
            return prepareErrorMessage(
                    ((CustomMessageDeliveryException) ex).getErrorCode(),
                    ((CustomMessageDeliveryException) ex).getErrorCode().getMessage()
            );
        }

        System.out.println("!@$!@$A");

        return super.handleClientMessageProcessingError(clientMessage, ex);
    }

    private Message<byte[]> prepareErrorMessage(ErrorCode errorCode, String errorMessage) {
        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);

        accessor.setMessage(errorCode.getCode());
        accessor.setNativeHeader("errorCode", errorCode.getCode());
        accessor.setNativeHeader("errorMessage", errorCode.getMessage());
        accessor.setLeaveMutable(true);

        return MessageBuilder.createMessage(errorMessage.getBytes(StandardCharsets.UTF_8), accessor.getMessageHeaders());
    }
}
