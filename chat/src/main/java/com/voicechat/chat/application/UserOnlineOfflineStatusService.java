package com.voicechat.chat.application;

import com.voicechat.chat.constant.UserOnlineStatus;
import com.voicechat.chat.dto.UserOnlineStatusEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserOnlineOfflineStatusService {
    private static final String UNIQUE_KEY = UUID.randomUUID().toString();
    private static final String ONLINE_STATUS_PREFIX_KEY = "CHANNEL_CHAT_ONLINE";

    private final RedisTemplate redisTemplate;
    public final ApplicationEventPublisher publisher;

    private String generateKey(Long userId, String sessionId) {
        return ONLINE_STATUS_PREFIX_KEY + "_" + userId + "_" + sessionId + "_" + UNIQUE_KEY;
    }

    public void addOnlineUser(Long userId, String sessionId) {
        final ValueOperations<String, String> valueOperations
            = this.redisTemplate.opsForValue();

        valueOperations.set(this.generateKey(userId, sessionId), userId.toString(), Duration.ofSeconds(30));

        publisher.publishEvent(new UserOnlineStatusEvent(
            userId, UserOnlineStatus.ONLINE
        ));
    }

    public void offlineUser(Long userId, String sessionId) {
        this.redisTemplate.delete(this.generateKey(userId, sessionId));

        this.userOnlineOfflineStatusRefresh(userId);
    }

    public boolean isOnlineUser(Long userId) {
        return this.redisTemplate.keys(ONLINE_STATUS_PREFIX_KEY + "_" + userId + "_*").size() > 0;
    }

    private void userOnlineOfflineStatusRefresh(Long userId) {
        if (this.isOnlineUser(userId)) {
            return;
        }

        this.offlineUser(userId);
    }

    public void offlineUser(Long userId) {
        publisher.publishEvent(new UserOnlineStatusEvent(
                userId, UserOnlineStatus.OFFLINE
        ));
    }
}
