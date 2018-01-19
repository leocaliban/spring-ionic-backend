package com.leocaliban.loja.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.leocaliban.loja.domain.Cliente;
import com.leocaliban.loja.dto.ClienteDTO;
import com.leocaliban.loja.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<?> buscar(@PathVariable Long id) {
		Cliente obj = service.buscar(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public ResponseEntity<Void> editar(@Valid @RequestBody ClienteDTO objDTO, @PathVariable Long id){
		Cliente obj = service.fromDTO(objDTO);
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
	public ResponseEntity<List<ClienteDTO>> listarTodos() {
		List<Cliente> categorias = service.listarTodos();
		//Converte a lista categorias para categoriasDTO - percorre a lista e realiza uma operação para cada objeto e depois converter
		List<ClienteDTO> categoriasDTO = categorias.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(categoriasDTO);
	}
	
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> buscarPagina(
			@RequestParam(value="page", defaultValue="0") Integer pagina, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linhasPorPagina, 
			@RequestParam(value="orderBy", defaultValue="nome")String ordenarPor ,
			@RequestParam(value="direction", defaultValue="ASC") String direcao){
		Page<Cliente> categorias = service.buscarPagina(pagina, linhasPorPagina, ordenarPor, direcao);
		//Converte a lista categorias para categoriasDTO - percorre a lista e realiza uma operação para cada objeto e depois converter
		Page<ClienteDTO> categoriasDTO = categorias.map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok().body(categoriasDTO);
	}

}
