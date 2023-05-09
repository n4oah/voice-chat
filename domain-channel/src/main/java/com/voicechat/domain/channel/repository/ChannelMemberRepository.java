package com.voicechat.domain.channel.repository;

import com.voicechat.domain.channel.entity.Channel;
import com.voicechat.domain.channel.entity.ChannelMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChannelMemberRepository extends JpaRepository<ChannelMember, Long> {
    Long countByChannel(Channel channel);
    Optional<ChannelMember> findByChannelAndUserId(Channel channel, Long userId);
}
