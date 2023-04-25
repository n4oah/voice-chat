package com.voicechat.domain.user;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nick_name", nullable = false, unique = true)
    String nickName;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    Set<SocialLogin> socialLogins = new HashSet<>();
}
