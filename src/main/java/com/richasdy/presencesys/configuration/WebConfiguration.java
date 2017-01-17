package com.richasdy.presencesys.configuration;

import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.dialect.springdata.SpringDataDialect;

@Configuration
public class WebConfiguration {

	@Bean
	public SpringDataDialect springDataDialect() {
		return new SpringDataDialect();
	}
	
}
