package com.leocaliban.loja.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.leocaliban.loja.domain.Cliente;
import com.leocaliban.loja.repositories.ClienteRepository;
import com.leocaliban.loja.security.UserSpringSecurity;

/**
 * Classe que permite a busca pelo nome do usuário (email no nosso problema)
 * @author Leocaliban
 *
 * 14 de mar de 2018
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private ClienteRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cliente = repository.findByEmail(email);
		if( cliente == null ) {
			throw new UsernameNotFoundException(email);
		}
		return new UserSpringSecurity(cliente.getId(), cliente.getEmail(), cliente.getSenha(), cliente.getPerfis());
	}

}
