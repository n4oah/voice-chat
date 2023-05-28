package com.voicechat.gateway.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration()
public class WebClientConfig {
    @LoadBalanced()
    @Bean("userServiceWebClient")
    public WebClient.Builder userServiceWebClient() {
        return WebClient.builder().baseUrl("http://user");
    }

    @LoadBalanced()
    @Bean("channelServiceWebClient")
    public WebClient.Builder channelServiceWebClient() {
        return WebClient.builder().baseUrl("http://channel");
    }
}
