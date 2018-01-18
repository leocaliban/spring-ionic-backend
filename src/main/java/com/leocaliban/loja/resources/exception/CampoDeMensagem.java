package com.leocaliban.loja.resources.exception;

import java.io.Serializable;

/**
 * Classe auxiliar para capturar as mensagens de erro dos campos de validações
 * @author Leocaliban
 *
 */
public class CampoDeMensagem  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String nomeCampo;
	private String mensagem;
	
	public CampoDeMensagem() {
		
	}

	public CampoDeMensagem(String nomeCampo, String mensagem) {
		super();
		this.nomeCampo = nomeCampo;
		this.mensagem = mensagem;
	}

	public String getNomeCampo() {
		return nomeCampo;
	}

	public void setNomeCampo(String nomeCampo) {
		this.nomeCampo = nomeCampo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}
