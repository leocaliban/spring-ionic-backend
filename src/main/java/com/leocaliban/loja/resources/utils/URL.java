package com.leocaliban.loja.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe {@link URL} utilitária para manipulação de dados URL.
 * 
 * @author Leocaliban
 *
 * 10 de mar de 2018
 */
public class URL {

	/**
	 * Faz a conversão de String para Long, recuperando somente os números.
	 * @param s String da URL (/categoria=1,2,3)
	 * @return lista de inteiros.
	 */
	public static List<Long> decodeIntList(String s){
		//retirando as virgulas da string
		String[] vetor = s.split(",");
		//Lista de inteiros
		List<Long> list = new ArrayList<>();
		//percorre a string que foi inserida no vetor e converte em inteiro inserindo na nova lista de inteiros
		for (int i = 0; i < vetor.length; i++) {
			list.add(Long.parseLong(vetor[i]));
		}
		return list;
		//Usando lambda
		//return Arrays.asList(s.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
	}
	
	/**
	 * Método que faz o decode de strings removendo os espaços em branco quando surgirem na URL
	 * @param s String da URL (/nome=no me)
	 * @return nome sem espaços
	 */
	public static String decodeParam(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} 
		catch (UnsupportedEncodingException e) {
			return "";
		}
	}
}
