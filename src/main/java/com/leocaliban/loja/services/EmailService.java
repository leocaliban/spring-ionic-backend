package com.leocaliban.loja.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.leocaliban.loja.domain.Pedido;

/**
 * Interface {@link EmailService} que define quais operações o serviço de email deve oferecer.
 * @author Leocaliban
 *
 * 11 de mar de 2018
 */
public interface EmailService {
	
	/**
	 * Envia a confirmação de pedido com texto plano.
	 * @param obj Pedido
	 */
	public void sendOrderConfirmationEmail(Pedido obj);
	
	/**
	 * Envia o email com texto plano.
	 * @param mensagem mensagem do corpo do email
	 */
	public void sendEmail(SimpleMailMessage mensagem);
	
	/**
	 * Envia a confirmação de pedido em HTML.
	 * @param obj Pedido
	 */
	void sendOrderConfirmationHtmlEmail(Pedido obj);
	
	/**
	 * Envia o email com corpo html
	 * @param msg mensagem HTMl
	 */
	void sendHtmlEmail(MimeMessage msg);

}
