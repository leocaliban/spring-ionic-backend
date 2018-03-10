package com.leocaliban.loja.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.leocaliban.loja.domain.Produto;
import com.leocaliban.loja.dto.ProdutoDTO;
import com.leocaliban.loja.resources.utils.URL;
import com.leocaliban.loja.services.ProdutoService;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService service;
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<Produto> buscar(@PathVariable Long id) {
		Produto obj = service.buscar(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> buscarPagina(
			@RequestParam(value="nome", defaultValue="") String nome,
			@RequestParam(value="categorias", defaultValue="") String categorias,
			@RequestParam(value="page", defaultValue="0") Integer pagina, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linhasPorPagina, 
			@RequestParam(value="orderBy", defaultValue="nome")String ordenarPor ,
			@RequestParam(value="direction", defaultValue="ASC") String direcao){
		
		List<Long> ids = URL.decodeIntList(categorias);
		String nomeDecoded = URL.decodeParam(nome);
		Page<Produto> list = service.pesquisar(nomeDecoded, ids, pagina, linhasPorPagina, ordenarPor, direcao);
		
		Page<ProdutoDTO> produtosDTO = list.map(obj -> new ProdutoDTO(obj));
		return ResponseEntity.ok().body(produtosDTO);
	}
}
