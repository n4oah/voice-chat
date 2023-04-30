package com.voicechat.domain.user.entity;

import com.voicechat.common.domain.AbstractAuditingEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Entity
@Getter
public class User extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull()
    @Size(min=4, max=50)
    @Email()
    @Column(name = "email", length = 50, nullable = false, unique = true)
    private String email;

    @NotNull()
    @Size(min=60, max=60)
    @Column(name = "password", length = 60, nullable = false, columnDefinition = "CHAR(60)")
    private String password;

    @NotNull()
    @Size(min = 2, max = 10)
    @Column(name = "name", length = 20, nullable = false)
    private String name;

    public User() {

    }

    public static User createUser(String email, String password, String name) {
        User user = new User();
        user.email = email;
        user.password = password;
        user.name = name;

        return user;
    }
}
