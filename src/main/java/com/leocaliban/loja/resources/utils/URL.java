package com.leocaliban.loja.resources.utils;

import java.util.ArrayList;
import java.util.List;

public class URL {

	public static List<Integer> decodeIntList(String s){
		String[] vetor = s.split(",");
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < vetor.length; i++) {
			list.add(Integer.parseInt(vetor[i]));
		}
		return list;
		//return Arrays.asList(s.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
	}
}
