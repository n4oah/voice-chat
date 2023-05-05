package com.voicechat.domain.channel.repository;

import com.voicechat.common.constant.ChannelMemberRole;
import com.voicechat.domain.channel.entity.ChannelMemberAuthRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelMemberAuthRoleRepository
        extends JpaRepository<ChannelMemberAuthRole, ChannelMemberRole> {
    ChannelMemberAuthRole findByName(ChannelMemberRole role);
}
