package com.leocaliban.loja.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.leocaliban.loja.domain.Boleto;

/**
 * Classe {@link BoletoService} simula um critério para o vencimento do pagamento
 * @author Leocaliban
 *
 * 10 de mar de 2018
 */
@Service
public class BoletoService {

	/**
	 * Coloca o vencimento 7 dias após o instante da compra
	 * @param pagamentoBoleto Boleto
	 * @param instantePedido data do instante da compra
	 */
	public void preenceherPagamentoBoleto(Boleto pagamentoBoleto, Date instantePedido) {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(instantePedido);
		calendario.add(Calendar.DAY_OF_MONTH, 7);
		pagamentoBoleto.setDataVencimento(calendario.getTime());
	}
}
