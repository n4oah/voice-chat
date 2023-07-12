package com.voicechat.chat.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.voicechat.chat.adapter.out.UserServiceClient;
import com.voicechat.chat.adapter.out.dto.UserAuthJwtDecodeDto;
import com.voicechat.chat.application.UserOnlineOfflineStatusService;
import com.voicechat.chat.exception.error.CustomMessageDeliveryException;
import com.voicechat.common.error.ErrorCode;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.auth.BasicUserPrincipal;
import org.springframework.messaging.simp.SimpMessageType;
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
    private static final String BEARER_PREFIX = "Bearer ";
    private final UserServiceClient userServiceClient;
    private final UserOnlineOfflineStatusService userOnlineOfflineStatusService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor.getMessageType() == SimpMessageType.HEARTBEAT) {
            if (accessor.getUser().getName() != null) {
                final var userId = Long.parseLong(accessor.getUser().getName());
                this.userOnlineOfflineStatusService.addOnlineUser(userId, accessor.getSessionId());
            }

            return message;
        }

        switch (accessor.getCommand()) {
            case CONNECT:
            case SUBSCRIBE:
            case SEND:
                String authorization = String.valueOf(accessor.getNativeHeader("authorization"));

                if (authorization == null || authorization.equals("null")) {
                    throw new CustomMessageDeliveryException(ErrorCode.UNAUTHORIZED_ERROR);
                }
                authorization = authorization.substring(BEARER_PREFIX.length());

                try {
                    final var memberInfo = userServiceClient.authJwtDecodeToUser(new UserAuthJwtDecodeDto.UserAuthJwtDecodeReqDto(
                            authorization
                    )).getBody();

                    accessor.setUser(new BasicUserPrincipal(memberInfo.id().toString()));

                    this.userOnlineOfflineStatusService.addOnlineUser(memberInfo.id(), accessor.getSessionId());
                } catch (FeignException exception) {
                    log.error("e", exception);
                    this.throwFeignException(exception);
                }
                break;
            case DISCONNECT:
                if (accessor.getUser() != null && accessor.getUser().getName() != null) {
                    final var userId = Long.parseLong(accessor.getUser().getName());
                    this.userOnlineOfflineStatusService.offlineUser(userId, accessor.getSessionId());
                }
                break;
            default:
                break;
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