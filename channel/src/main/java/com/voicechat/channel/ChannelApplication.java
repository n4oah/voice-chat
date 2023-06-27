package com.voicechat.channel;

import com.voicechat.common.error.GlobalHttpExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableFeignClients
@Import(GlobalHttpExceptionHandler.class)
public class ChannelApplication {
	public static void main(String[] args) {
		SpringApplication.run(ChannelApplication.class, args);
	}

}
