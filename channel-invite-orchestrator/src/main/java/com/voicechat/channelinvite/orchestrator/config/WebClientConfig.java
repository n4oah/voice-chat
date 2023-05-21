package com.voicechat.channelinvite.orchestrator.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @LoadBalanced()
    @Bean("channelInviteWebClient")
    public WebClient.Builder channelInviteWebClient() {
        return WebClient.builder().baseUrl("http://channel-invite");
    }

    @LoadBalanced()
    @Bean("channelWebClient")
    public WebClient.Builder channelWebClient() {
        return WebClient.builder().baseUrl("http://channel");
    }
}
