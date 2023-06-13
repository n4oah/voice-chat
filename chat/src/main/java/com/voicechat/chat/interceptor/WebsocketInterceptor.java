package com.voicechat.chat.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.voicechat.chat.adapter.out.UserServiceClient;
import com.voicechat.chat.adapter.out.dto.UserAuthJwtDecodeDto;
import com.voicechat.chat.exception.error.CustomMessageDeliveryException;
import com.voicechat.common.error.ErrorCode;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@RequiredArgsConstructor
@Component
@Slf4j
public class WebsocketInterceptor implements ChannelInterceptor {
    private final UserServiceClient userServiceClient;
    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(headerAccessor.getCommand())
            || StompCommand.SUBSCRIBE.equals(headerAccessor.getCommand())
            || StompCommand.SEND.equals(headerAccessor.getCommand())) {

            String authorization = String.valueOf(headerAccessor.getNativeHeader("authorization"));

            if (authorization == null || authorization.equals("null")) {
                throw new CustomMessageDeliveryException(ErrorCode.UNAUTHORIZED_ERROR);
            }
            authorization = authorization.substring(BEARER_PREFIX.length());

            try {
                final var memberInfo = userServiceClient.authJwtDecodeToUser(new UserAuthJwtDecodeDto.UserAuthJwtDecodeReqDto(
                    authorization
                )).getBody();

                System.out.println(memberInfo.id());
            } catch (FeignException exception) {
                log.error("e", exception);
                this.throwFeignException(exception);
            }
        }

        return message;
    }

    private void throwFeignException(FeignException exception) {
        final ObjectMapper objectMapper = new ObjectMapper();
        final String responseBody = new String(exception.responseBody().get().array(), StandardCharsets.UTF_8);
        try {
            final Map<String, Object> response = objectMapper.readValue(responseBody, new TypeReference<>() {});
            if (response.containsKey("code")) {
                ErrorCode errorCode = ErrorCode.getErrorCodeByCode((String) response.get("code")).orElseThrow(() -> exception);
                throw new CustomMessageDeliveryException(errorCode);
            }
            throw exception;
        } catch (JsonProcessingException e) {
            throw exception;
        }
    }
}