package com.voicechat.chat.adapter.out;

import com.voicechat.chat.adapter.out.dto.UserAuthJwtDecodeDto;
import com.voicechat.common.vo.UserJwtClaim;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="user")
public interface UserServiceClient {
    @GetMapping("/auth/jwt/decode")
    ResponseEntity<UserJwtClaim> authJwtDecodeToUser(
            @SpringQueryMap() UserAuthJwtDecodeDto.UserAuthJwtDecodeReqDto authJwtDecodeReqDto
    );
}
