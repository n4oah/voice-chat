package com.voicechat.domain.channelinvite.entity;

import com.voicechat.common.constant.ChannelInviteStatus;
import com.voicechat.common.domain.AbstractAuditingEntity;
import com.voicechat.common.event.Events;
import com.voicechat.domain.channelinvite.event.ApprovedChannelInviteEvent;
import com.voicechat.domain.channelinvite.service.ChannelInviteChecker;
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

    public static ChannelInvite inviteChannel(Long channelId, Long userId, ChannelInviteChecker channelInviteChecker) {
        channelInviteChecker.verifyCreateChannelInvite(channelId, userId);

        final var channelInvite = new ChannelInvite();
        channelInvite.invitedChannelId = channelId;
        channelInvite.invitedUserId = userId;
        channelInvite.status = ChannelInviteStatus.WAITED;

        return channelInvite;
    }

    public void approveInvitedChannel(ChannelInviteChecker channelInviteChecker) {
        channelInviteChecker.verifyCreateChannelInvite(this.getInvitedChannelId(), this.getInvitedUserId());

        this.status = ChannelInviteStatus.ACCEPTED;

        Events.raise(new ApprovedChannelInviteEvent(this.id));
    }

    public void rejectInvitedChannel() {
        this.status = ChannelInviteStatus.REJECTED;
    }

    public void refuseInvitedChannel() {
        this.status = ChannelInviteStatus.REFUSED;
    }
}
