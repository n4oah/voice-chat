package com.voicechat.domain.channel.entity;

import com.voicechat.common.constant.ChannelMemberRole;
import com.voicechat.domain.channel.repository.ChannelMemberAuthRoleRepository;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "channel_member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChannelMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "channel_id", nullable = false)
    private Channel channel;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "channel_member_auth",
            joinColumns = {@JoinColumn(name = "channel_member_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_name", referencedColumnName = "name")})
    private Set<ChannelMemberAuthRole> authorities = new HashSet<>();

    public static ChannelMember createChannelMember(
            Channel channel,
            Long userId,
            ChannelMemberRole role,
            ChannelMemberAuthRoleRepository channelMemberAuthRoleRepository
        ) {
        final var channelMember = new ChannelMember();
        Set<ChannelMemberAuthRole> roles = new HashSet<>();
        channelMember.channel = channel;

        channelMember.userId = userId;
        
        switch (role) {
            case MEMBER ->
                roles.add(channelMemberAuthRoleRepository.findByName(ChannelMemberRole.MEMBER));
            case ASSISTANT_MANAGER -> {
                roles.add(channelMemberAuthRoleRepository.findByName(ChannelMemberRole.MEMBER));
                roles.add(channelMemberAuthRoleRepository.findByName(ChannelMemberRole.ASSISTANT_MANAGER));
            }
            case MANAGER -> {
                roles.add(channelMemberAuthRoleRepository.findByName(ChannelMemberRole.MEMBER));
                roles.add(channelMemberAuthRoleRepository.findByName(ChannelMemberRole.ASSISTANT_MANAGER));
                roles.add(channelMemberAuthRoleRepository.findByName(ChannelMemberRole.MANAGER));
            }
        }

        channelMember.authorities = roles;

        return channelMember;
    }
}
