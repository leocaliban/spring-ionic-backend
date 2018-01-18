package com.leocaliban.loja.resources.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de erro com a captura das mensagens
 * @author Leocaliban
 *
 */
public class ErroValidacao extends ErroPadrao{

	private static final long serialVersionUID = 1L;

	private List<CampoDeMensagem> erros = new ArrayList<>();
	public ErroValidacao(Integer status, String mensagem, Long timeStamp) {
		super(status, mensagem, timeStamp);

	}
	public List<CampoDeMensagem> getErros() {
		return erros;
	}
	//Set substituído para que o erro seja adicionado na lista um a um
	public void adicionarErro(String nomeCampo, String mensagem) {
		erros.add(new CampoDeMensagem(nomeCampo, mensagem));
	}

	
	
}
