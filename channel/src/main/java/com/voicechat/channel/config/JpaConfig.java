package com.voicechat.channel.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.voicechat.domain.channel")
@EnableJpaRepositories(basePackages="com.voicechat.domain.channel")
@Configuration
public class JpaConfig {
}
