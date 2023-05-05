package com.voicechat.domain.user.repository;

import com.voicechat.common.constant.UserRole;
import com.voicechat.domain.user.entity.UserAuthRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthRoleRepository extends JpaRepository<UserAuthRole, UserRole> {
    UserAuthRole findByName(UserRole userRole);
}
