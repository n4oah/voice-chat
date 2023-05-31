package com.voicechat.channelinvite.adapter.out;

import com.voicechat.channelinvite.adapter.dto.GetUserDto;
import com.voicechat.common.constant.HeaderKey;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="user")
public interface UserServiceClient {
    @GetMapping("/me")
    ResponseEntity<GetUserDto.GetUserResDto> getUserInfo(
        @RequestHeader(HeaderKey.USER_ID) Long userId
    );

    @GetMapping("/by-email/{email}")
    ResponseEntity<GetUserDto.GetUserResDto> getUserInfoByEmail(
            @PathVariable("email") String email
    );
}
