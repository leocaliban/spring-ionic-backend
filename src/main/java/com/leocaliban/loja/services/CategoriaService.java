package com.leocaliban.loja.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.leocaliban.loja.domain.Categoria;
import com.leocaliban.loja.repositories.CategoriaRepository;
import com.leocaliban.loja.services.exceptions.IntegridadeDeDadosException;
import com.leocaliban.loja.services.exceptions.ObjetoNaoEncontradoException;

@Service
public class CategoriaService {
	
	//Instancia automaticamente a dependencia
	@Autowired
	private CategoriaRepository repository;
	public Categoria buscar(Long id) {
		Categoria obj = repository.findOne(id);
		if (obj == null) {
			throw new ObjetoNaoEncontradoException("Objeto Não Encontrado! Id: "+id+", Tipo: "
					+Categoria.class.getName());
		}
		return obj;
	}
	
	public Categoria salvar(Categoria obj) {
		//Garante que o objeto seja salvo como um novo e não como atualização 
		obj.setId(null);
		return repository.save(obj);
	}
	
	public Categoria editar(Categoria obj) {
		//Verificação se o id existe
		buscar(obj.getId());
		return repository.save(obj);
	}
	
	public void excluir(Long id) {
		buscar(id);
		try {
			repository.delete(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new IntegridadeDeDadosException("Não é possível excluir uma categoria que possui produtos.");
		}
	}
}
