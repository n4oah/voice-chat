package com.voicechat.domain.channel.entity;

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
@Table(name = "channels")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Channel extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "max_number_of_member", nullable = false)
    private Integer maxNumberOfMember;

    // 채널 멤버도 새로운 서비스로 빼야하는데, 일단 channel 서비스에 포함
    @OneToMany(mappedBy = "channel", orphanRemoval = true, cascade = {CascadeType.ALL})
    private List<ChannelMember> channelMembers = new ArrayList<>();

    public static Channel createChannel(
            Long createdUserId,
            String name,
            Integer maxNumberOfMember,
            ChannelMemberAuthRoleRepository channelMemberAuthRoleRepository
    ) {
        final var channel = new Channel();
        channel.name = name;
        channel.maxNumberOfMember = maxNumberOfMember;

        final var channelMember =
                ChannelMember.createChannelMember(
                        channel,
                        createdUserId,
                        ChannelMemberRole.MANAGER,
                        channelMemberAuthRoleRepository
                );
        channel.channelMembers.add(channelMember);

        return channel;
    }
}
