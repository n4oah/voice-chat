package com.voicechat.user.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("com.voicechat.domain.user")
@EnableJpaRepositories(basePackages="com.voicechat.domain.user")
public class JpaConfig {
}
