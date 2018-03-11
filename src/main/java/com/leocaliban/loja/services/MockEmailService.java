package com.leocaliban.loja.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

/**
 * Classe {@link MockEmailService} implementa o envio de email para o log via Mock.
 * @author Leocaliban
 *
 * 11 de mar de 2018
 */
public class MockEmailService extends AbstractEmailService{

	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);
	@Override
	public void sendEmail(SimpleMailMessage mensagem) {
		LOG.info("Simulando envio de email...");
		LOG.info(mensagem.toString());
		LOG.info("Email enviado!");
	}

}
