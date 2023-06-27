package com.voicechat.channel.adapter.out;

import com.voicechat.channel.adapter.dto.GetUserDto;
import com.voicechat.common.constant.HeaderKey;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="user")
public interface UserServiceClient {
    @GetMapping("/me")
    ResponseEntity<GetUserDto.GetUserResDto> getUserInfo(
        @RequestHeader(HeaderKey.USER_ID) Long userId
    );
}
