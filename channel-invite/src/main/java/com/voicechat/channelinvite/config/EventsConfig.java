package com.voicechat.channelinvite.config;

import com.voicechat.common.event.Events;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventsConfig {
    @Bean
    public InitializingBean eventsInit(ApplicationContext applicationContext) {
        return () -> Events.setPublisher(applicationContext);
    }
}
