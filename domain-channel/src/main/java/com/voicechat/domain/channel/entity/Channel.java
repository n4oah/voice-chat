package com.voicechat.domain.channel.entity;

import com.voicechat.common.constant.ChannelMemberRole;
import com.voicechat.common.domain.AbstractAuditingEntity;
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

    @OneToMany(mappedBy = "channel", orphanRemoval = true, cascade = {CascadeType.ALL})
    private List<ChannelMember> channelMembers = new ArrayList<>();

    public static void createChannel(String name, Integer maxNumberOfMember, Long createdUserId) {
        final var channel = new Channel();
        channel.name = name;
        channel.maxNumberOfMember = maxNumberOfMember;

        final var channelMember = ChannelMember.createChannelMember(createdUserId, ChannelMemberRole.MANAGER);
        channel.channelMembers.add(channelMember);
    }
}
