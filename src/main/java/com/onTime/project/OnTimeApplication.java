package com.onTime.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories(basePackages="com.onTime.project.model.dao")
@EnableElasticsearchRepositories(basePackages="com.onTime.project.model.dao")
@EntityScan({"com.onTime.project.model.domain", "com.onTime.project.model.es"})
@SpringBootApplication
public class OnTimeApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnTimeApplication.class, args);
	}

}