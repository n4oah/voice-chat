package com.voicechat.domain.channelinvite.entity;

import com.voicechat.common.constant.ChannelMemberRole;
import com.voicechat.common.domain.AbstractAuditingEntity;
import com.voicechat.domain.channel.repository.ChannelMemberAuthRoleRepository;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "channel_invite")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChannelInvite extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
