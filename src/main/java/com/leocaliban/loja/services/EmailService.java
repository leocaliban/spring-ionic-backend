package com.leocaliban.loja.services;

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
	 * Envia a confirmação de pedido.
	 * @param obj Pedido
	 */
	public void sendOrderConfirmationEmail(Pedido obj);
	
	/**
	 * Envia o email.
	 * @param mensagem mensagem
	 */
	public void sendEmail(SimpleMailMessage mensagem);
	

}
