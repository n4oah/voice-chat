package com.voicechat.domain.user.entity;

import com.voicechat.common.constant.UserRole;
import com.voicechat.common.domain.AbstractAuditingEntity;
import com.voicechat.domain.user.repository.UserAuthRoleRepository;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "users")
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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_auth",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_name", referencedColumnName = "name")})
    private Set<UserAuthRole> authorities = new HashSet<>();

    public User() {

    }

    public static User createUser(
            String email,
            String password,
            String name,
            UserAuthRoleRepository userAuthRoleRepository
    ) {
        User user = new User();
        user.email = email;
        user.password = password;
        user.name = name;

        final var userAuthRole = userAuthRoleRepository.findByName(UserRole.USER);
        user.authorities.add(userAuthRole);

        return user;
    }
}
