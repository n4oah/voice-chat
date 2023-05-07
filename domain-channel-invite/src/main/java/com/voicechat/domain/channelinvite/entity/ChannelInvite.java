package com.voicechat.domain.channelinvite.entity;

import com.voicechat.common.domain.AbstractAuditingEntity;
import com.voicechat.domain.channelinvite.constants.ChannelInviteStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "channel_invite")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChannelInvite extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "invited_channel_id", nullable = false)
    private Long invitedChannelId;

    @Column(name = "invited_user_id", nullable = false)
    private Long invitedUserId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ChannelInviteStatus status;
}
