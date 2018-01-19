package com.leocaliban.loja.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.leocaliban.loja.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	//Indica que a busca não está envolvida com uma transação com o banco
	@Transactional(readOnly=true)
	Cliente findByEmail(String email);

}
