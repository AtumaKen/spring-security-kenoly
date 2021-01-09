package com.kenoly.udemy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.kenoly.udemy.security.AppProperties;

@SpringBootApplication
public class SergeyKenolyUdemyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SergeyKenolyUdemyApplication.class, args);
	}

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	@Bean 
	SpringApplicationContext springApplicationContext() {
		return new SpringApplicationContext();
	}
	
	@Bean
	public AppProperties getAppProperties() {
		return new AppProperties();
	}

}
