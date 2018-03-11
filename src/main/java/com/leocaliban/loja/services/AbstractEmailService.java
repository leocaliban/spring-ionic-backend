package com.leocaliban.loja.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.leocaliban.loja.domain.Pedido;
/**
 * Classe abstrata {@link AbstractEmailService} responsável pela preparação do email
 * @author Leocaliban
 *
 * 11 de mar de 2018
 */
public abstract class AbstractEmailService implements EmailService {
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
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
	
	/**
	 * método responsável por retornar o HTML preenchido com os dados de um pedido, a partir do template Thymeleaf
	 * @param obj Pedido
	 * @return String contendo o resultado do modelo especificado com o contexto fornecido
	 */
	protected String htmlFromTemplatePedido(Pedido obj) {
		Context context = new Context();
		context.setVariable("pedido", obj);// *pedido apelido usado no tm "${pedido.id}" *obj pedido que o tm acessa
		return templateEngine.process("email/ConfirmacaoPedido", context);// caminho do html e o contexto montado 
	}
	
	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido obj) {
		try {
			MimeMessage mensagemHTML = prepareMimeMessageFromPedido(obj);
			sendHtmlEmail(mensagemHTML);
		}
		catch (MessagingException e) {
			sendOrderConfirmationEmail(obj);
		}

	}
	
	/**
	 * Cria a estrutura do email HTML
	 * @param obj Pedido
	 * @return mensagem do email
	 * @throws MessagingException 
	 */
	protected MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
		mimeMessageHelper.setTo(obj.getCliente().getEmail()); 
		mimeMessageHelper.setFrom(sender);
		mimeMessageHelper.setSubject("Pedido confirmado! Código: " + obj.getId());
		mimeMessageHelper.setSentDate(new Date(System.currentTimeMillis()));
		mimeMessageHelper.setText(htmlFromTemplatePedido(obj),true);
		return mimeMessage;
	}
}
