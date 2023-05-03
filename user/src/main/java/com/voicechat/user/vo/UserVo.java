package com.voicechat.user.vo;

import com.voicechat.common.constant.UserRole;
import lombok.Getter;
import org.springframework.security.core.userdetails.User;

import java.util.Set;

@Getter
public class UserVo extends User {
    private final Long id;
    private final String name;

    public UserVo(Long id, String email, String password, String name, Set<UserRole> userRoles) {
        super(email, password, userRoles);

        this.id = id;
        this.name = name;
    }
}
