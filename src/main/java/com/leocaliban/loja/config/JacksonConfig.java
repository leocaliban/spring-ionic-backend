package com.leocaliban.loja.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leocaliban.loja.domain.Boleto;
import com.leocaliban.loja.domain.CartaoCredito;

/**
 * Classe {@link JacksonConfig} Responsável pelas configurações do Jackson. (Padrão)
 * @author Leocaliban
 *
 * 10 de mar de 2018
 */
@Configuration
public class JacksonConfig {

	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
			public void configure(ObjectMapper objectMapper) {
				objectMapper.registerSubtypes(CartaoCredito.class);
				objectMapper.registerSubtypes(Boleto.class);
				super.configure(objectMapper);
			};
		};
		return builder;
	}
}
