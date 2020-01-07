package com.bigbang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.bigbang.api.service.impl.UserServiceImpl;

@SpringBootApplication(scanBasePackages = { "com.bigbang.*" })
@CrossOrigin
@EnableAutoConfiguration
public class BigBangApplication {

	@Autowired
	UserServiceImpl userServiceImpl =null;

	
	public static void main(String[] args) {
		SpringApplication.run(BigBangApplication.class, args);
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() throws Exception {
	    userServiceImpl.getUserByUuid("alien");
	}

}
