package com.voicechat.domain.channel.entity;

import com.voicechat.common.constant.ChannelMemberRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "channel_member_auth_role")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChannelMemberAuthRole {
    @Id
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "name")
    private ChannelMemberRole name;

    public ChannelMemberAuthRole(ChannelMemberRole name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChannelMemberAuthRole)) {
            return false;
        }

        return this.name.equals(((ChannelMemberAuthRole) o).getName());
    }
}
