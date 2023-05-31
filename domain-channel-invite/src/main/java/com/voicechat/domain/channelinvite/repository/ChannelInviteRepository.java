package com.voicechat.domain.channelinvite.repository;

import com.voicechat.common.constant.ChannelInviteStatus;
import com.voicechat.domain.channelinvite.entity.ChannelInvite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChannelInviteRepository extends JpaRepository<ChannelInvite, Long> {
    Optional<ChannelInvite> findByInvitedChannelIdAndInvitedUserIdAndStatus(
            Long invitedChannelId,
            Long invitedUserId,
            ChannelInviteStatus status
    );
    Optional<ChannelInvite> findByIdAndInvitedUserIdAndStatus(
            Long id,
            Long invitedUserId,
            ChannelInviteStatus status
    );

    List<ChannelInvite> findByInvitedUserIdAndStatus(Long invitedUserId, ChannelInviteStatus status);
    List<ChannelInvite> findByInvitedChannelIdAndStatus(Long invitedChannelId, ChannelInviteStatus status);
}
