package com.voicechat.chat.interceptor;

import com.voicechat.chat.adapter.out.UserServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

@RequiredArgsConstructor
@Component
public class WebsocketInterceptor implements ChannelInterceptor {
    private final UserServiceClient userServiceClient;
    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);

        String authorization = String.valueOf(headerAccessor.getNativeHeader("authorization"));

        System.out.println("authorization" + authorization);

//        if(authorizationHeader == null){
//            throw new MessageDeliveryException("메세지 예외");
//        }
//
//        String token = authorizationHeader.substring(BEARER_PREFIX.length());
//
//        // 토큰 인증
//        Claims claims;
//        try{
//            claims = jwtService.verifyToken(token, jwtProperties.getAccessTokenSigningKey());
//        }catch (MessageDeliveryException e){
//            throw new MessageDeliveryException("메세지 에러");
//        }catch (MalformedJwtException e){
//            throw new MessageDeliveryException("예외3");
//        }

        return message;
    }
}