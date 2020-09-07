package com.maykon.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.maykon.cursomc.services.DBService;
import com.maykon.cursomc.services.EmailService;
import com.maykon.cursomc.services.MockEmailService;
import com.maykon.cursomc.services.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfig {

	@Autowired
	private DBService dbService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	 
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		System.out.println(strategy);
		if (!"create".equals(strategy)){
			return false;
		}
		dbService.instantiateTestDatabase();
		return true;
		
	}
	
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
	
	@Bean
	public SmtpEmailService smtpEmail() {
		return new SmtpEmailService();
	}
}
