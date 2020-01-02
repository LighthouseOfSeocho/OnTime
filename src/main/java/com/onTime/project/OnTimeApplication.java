package com.onTime.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.jackson.datatype.VavrModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
@EnableJpaRepositories(basePackages="com.onTime.project.model.dao")
@EnableElasticsearchRepositories(basePackages="com.onTime.project.model.dao")
@EntityScan({"com.onTime.project.model.domain", "com.onTime.project.model.es"})
@SpringBootApplication
//@EnableJpaRepositories(basePackages= {"com.onTime.project.model.dao"})
//@EntityScan("com.onTime.project.model.domain")
@ComponentScan({"com.onTime.project.domain","com.onTime.project.controller","com.onTime.project.loginAPI","com.onTime.project.model.dao","com.onTime.project.model.domain","com.onTime.project.service","com.onTime.project.test","com.onTime.project.chat.*","com.onTime.project.app.*", "com.ontime.project.model.es.*"})
public class OnTimeApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnTimeApplication.class, args);
	}
	@Bean
	public ObjectMapper jacksonBuilder() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.registerModule(new VavrModule());
	}
}

