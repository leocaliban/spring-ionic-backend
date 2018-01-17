package com.leocaliban.loja.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.leocaliban.loja.domain.Categoria;
import com.leocaliban.loja.dto.CategoriaDTO;
import com.leocaliban.loja.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<Categoria> buscar(@PathVariable Long id) {
		Categoria obj = service.buscar(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	//@RequestBody converte json em java
	public ResponseEntity<Void> salvar(@RequestBody Categoria obj){
		obj = service.salvar(obj);
		//Retorno da uri de sucesso ao criar 201
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public ResponseEntity<Void> editar(@RequestBody Categoria obj, @PathVariable Long id){
		obj.setId(id);
		obj = service.editar(obj);
		//Sucesso 204 não precisa de uri retorno 
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		service.excluir(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> listarTodos() {
		List<Categoria> categorias = service.listarTodos();
		//Converte a lista categorias para categoriasDTO - percorre a lista e realiza uma operação para cada objeto e depois converter
		List<CategoriaDTO> categoriasDTO = categorias.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(categoriasDTO);
	}
		
}
