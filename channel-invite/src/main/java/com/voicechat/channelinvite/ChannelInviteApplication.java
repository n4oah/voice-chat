package com.voicechat.channelinvite;

import com.voicechat.common.error.GlobalHttpExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableFeignClients
@Import(GlobalHttpExceptionHandler.class)
public class ChannelInviteApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChannelInviteApplication.class, args);
	}

}
