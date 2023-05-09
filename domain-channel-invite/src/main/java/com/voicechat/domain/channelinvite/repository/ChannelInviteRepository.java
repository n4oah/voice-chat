package com.voicechat.domain.channelinvite.repository;

import com.voicechat.domain.channelinvite.entity.ChannelInvite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelInviteRepository extends JpaRepository<ChannelInvite, Long> {
    
}
