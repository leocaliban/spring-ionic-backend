package com.leocaliban.loja.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.leocaliban.loja.services.DBService;

/**
 * Classe {@link TestConfig} responsável pelas configurações específicas do profile de testes
 * @author Leocaliban
 *
 * 10 de mar de 2018
 */
@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	private DBService dbService;
	
	/**
	 * Instancia o bando de dados para meios de testes
	 * @return
	 * @throws ParseException 
	 */
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dbService.instantiateTestDatabase();	
		return true;
	}

}
