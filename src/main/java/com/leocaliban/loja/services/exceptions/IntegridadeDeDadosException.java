package com.leocaliban.loja.services.exceptions;

public class IntegridadeDeDadosException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IntegridadeDeDadosException (String mensagem) {
		super(mensagem);
	}
	public IntegridadeDeDadosException(String mensagem, Throwable cause) {
		super(mensagem, cause);
	}
}
