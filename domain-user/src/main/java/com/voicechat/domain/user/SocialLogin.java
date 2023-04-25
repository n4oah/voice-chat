package com.voicechat.domain.user;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "social_login")
public class SocialLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private SocialType type;

    @Type(JsonType.class)
    @Column(name = "raw_data", columnDefinition = "json")
    private Map<String, Object> rawData = new HashMap<>();
}
