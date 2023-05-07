package com.voicechat.domain.channelinvite.repository;

import com.voicechat.domain.channelinvite.entity.ChannelInvite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelInviteRepository extends JpaRepository<ChannelInvite, Long> {
    
}
