package com.onTime.project;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableJpaRepositories(basePackages= {"com.onTime.project.model.dao"})
@EntityScan("com.onTime.project.model.domain")
@ComponentScan({"com.onTime.project.domain","com.onTime.project.controller","com.onTime.project.loginAPI","com.onTime.project.model.dao","com.onTime.project.model.domain","com.onTime.project.service","com.onTime.project.test"})
public class OnTimeApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnTimeApplication.class, args);
	}
	@Component
	public class CORSFilter implements Filter {
	    @Override
	    public void init(FilterConfig filterConfig) throws ServletException {
	    }
	    @Override
	    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
	        HttpServletResponse response = (HttpServletResponse) servletResponse;
	        response.setHeader("Access-Control-Allow-Origin", "*");
	        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
	        response.setHeader("Access-Control-Max-Age", "3600");
	        response.setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, Accept");
	        response.setHeader("Access-Control-Expose-Headers", "Location");
	        filterChain.doFilter(servletRequest, servletResponse);
	    }
	    @Override
	    public void destroy() {
	    }
	}
}
