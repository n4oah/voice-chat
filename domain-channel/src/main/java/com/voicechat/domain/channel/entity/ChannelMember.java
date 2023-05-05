package com.voicechat.domain.channel.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "channel_member")
public class ChannelMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
