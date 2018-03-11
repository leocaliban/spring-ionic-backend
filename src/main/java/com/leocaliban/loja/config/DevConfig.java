package com.leocaliban.loja.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.leocaliban.loja.services.DBService;
import com.leocaliban.loja.services.EmailService;
import com.leocaliban.loja.services.SmtpEmailService;

/**
 * Classe {@link DevConfig} responsável pelas configurações específicas do profile de desenvolvimento
 * @author Leocaliban
 *
 * 10 de mar de 2018
 */
@Configuration
@Profile("dev")
public class DevConfig {
	
	@Autowired
	private DBService dbService;
	
	//recupera o valor de application-dev.properties
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	/**
	 * Instancia o banco de dados para meios de testes
	 * @return
	 * @throws ParseException 
	 */
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		if(!"create".equals(strategy)) {
			return false;
		}
		dbService.instantiateTestDatabase();	
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}

}
