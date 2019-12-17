package com.onTime.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages= {"com.onTime.project.model.dao"})
@EntityScan("com.onTime.project.model.domain")
@ComponentScan({"com.onTime.project.domain","com.onTime.project.controller","com.onTime.project.loginAPI","com.onTime.project.model.dao","com.onTime.project.model.domain","com.onTime.project.service","com.onTime.project.test"})
public class OnTimeApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnTimeApplication.class, args);
	}

}
