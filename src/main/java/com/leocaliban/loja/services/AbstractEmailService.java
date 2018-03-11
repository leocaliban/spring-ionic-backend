package com.leocaliban.loja.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.leocaliban.loja.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.sender=leocaliban@gmail.com}")
	private String sender;
	
	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage mensagem = prepareSimpleMailMessage(obj);
		sendEmail(mensagem);
	}

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
