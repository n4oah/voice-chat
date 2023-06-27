package com.voicechat.chat.application;

import com.voicechat.chat.adapter.out.UserServiceClient;
import com.voicechat.chat.adapter.out.dto.GetUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatUserDetailService {
    private final UserServiceClient userServiceClient;

    @Cacheable(value = "getUserDetailByUserId", cacheManager = "cacheManager", key = "#userId")
    public GetUserDto.GetUserResDto getUserDetail(Long userId) {
        return userServiceClient.getUserInfo(userId).getBody();
    }
}
