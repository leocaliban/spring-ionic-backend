package com.leocaliban.loja.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.leocaliban.loja.domain.Categoria;
import com.leocaliban.loja.domain.Produto;
import com.leocaliban.loja.repositories.CategoriaRepository;
import com.leocaliban.loja.repositories.ProdutoRepository;
import com.leocaliban.loja.services.exceptions.ObjetoNaoEncontradoException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Produto buscar(Long id) {
		Produto obj = produtoRepository.findOne(id);
		if (obj == null) {
			throw new ObjetoNaoEncontradoException("Objeto Não Encontrado! Id: "+id+", Tipo: "
					+Produto.class.getName());
		}
		return obj;
	}
	
	public Page<Produto> pesquisar(String nome, List<Long> ids,Integer pagina, 
									Integer linhasPorPagina, String ordenarPor, String direcao){
		
		PageRequest pageRequest = new PageRequest(pagina, linhasPorPagina, Direction.valueOf(direcao), ordenarPor);
		List<Categoria> categorias = categoriaRepository.findAll(ids);
		return produtoRepository.pesquisar(nome, categorias, pageRequest);
		
	}
}
