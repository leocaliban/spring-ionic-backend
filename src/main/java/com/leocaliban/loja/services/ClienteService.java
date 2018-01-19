package com.leocaliban.loja.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.leocaliban.loja.domain.Cliente;
import com.leocaliban.loja.dto.ClienteDTO;
import com.leocaliban.loja.repositories.ClienteRepository;
import com.leocaliban.loja.services.exceptions.IntegridadeDeDadosException;
import com.leocaliban.loja.services.exceptions.ObjetoNaoEncontradoException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	
	public Cliente buscar(Long id) {
		Cliente obj = repository.findOne(id);
		if (obj == null) {
			throw new ObjetoNaoEncontradoException("Objeto Não Encontrado! Id: "+id+", Tipo: "
					+Cliente.class.getName());
		}
		return obj;
	}
	
	public Cliente editar(Cliente obj) {
		Cliente novoCliente = buscar(obj.getId());
		atualizarObjeto(novoCliente, obj);
		buscar(obj.getId());
		return repository.save(novoCliente);
	}
	
	public void excluir(Long id) {
		buscar(id);
		try {
			repository.delete(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new IntegridadeDeDadosException("Não é possível excluir porque existe relação entre as entidades.");
		}
	}
	
	public List<Cliente> listarTodos(){
		return repository.findAll();
	}
	
	//Conv param - page, linesPerPage, orderBy, direction
	public Page<Cliente> buscarPagina(Integer pagina, Integer linhasPorPagina, String ordenarPor, String direcao){
		PageRequest pageRequest = new PageRequest(pagina, linhasPorPagina, Direction.valueOf(direcao), ordenarPor);
		return repository.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
	}
	
	/**
	 * Método auxiliar para atualizar os dados do cliente sem perdê-los
	 * @param novoCliente Cliente criado que será salvo como atualização
	 * @param obj Cliente recuperado do banco
	 */
	private void atualizarObjeto(Cliente novoCliente, Cliente obj) {
		novoCliente.setNome(obj.getNome());;
		novoCliente.setEmail(obj.getEmail());
	}
}
