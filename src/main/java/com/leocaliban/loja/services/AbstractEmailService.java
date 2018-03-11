package com.leocaliban.loja.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.leocaliban.loja.domain.Pedido;
/**
 * Classe abstrata {@link AbstractEmailService} responsável pela preparação do email
 * @author Leocaliban
 *
 * 11 de mar de 2018
 */
public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage mensagem = prepareSimpleMailMessage(obj);
		sendEmail(mensagem);
	}

	/**
	 * Cria a estrutura do email
	 * @param obj Pedido
	 * @return mensagem do email
	 */
	protected SimpleMailMessage prepareSimpleMailMessage(Pedido obj) {
		SimpleMailMessage mensagem = new SimpleMailMessage();
		mensagem.setTo(obj.getCliente().getEmail()); 					  // email do destinatario
		mensagem.setFrom(sender); 										  //email remetente recuperado de application
		mensagem.setSubject("Pedido confirmado! Código: " + obj.getId()); //assunto do email
		mensagem.setSentDate(new Date(System.currentTimeMillis()));       // data do email
		mensagem.setText(obj.toString());                                 //corpo do email
		return mensagem;
	}
}
