package com.voicechat.domain.user.entity;

import com.voicechat.common.constant.UserRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "user_auth_role")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAuthRole {
    @Id
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "name")
    private UserRole name;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserAuthRole)) {
            return false;
        }

        return this.name.equals(((UserAuthRole) o).getName());
    }
}