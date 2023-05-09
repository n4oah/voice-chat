package com.voicechat.channelinvite.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("com.voicechat.domain.channelinvite")
@EnableJpaRepositories(basePackages="com.voicechat.domain.channelinvite")
public class JpaConfig {
}
