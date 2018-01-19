package com.leocaliban.loja.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.leocaliban.loja.domain.Cliente;
import com.leocaliban.loja.domain.enums.TipoCliente;
import com.leocaliban.loja.dto.ClienteNovoDTO;
import com.leocaliban.loja.repositories.ClienteRepository;
import com.leocaliban.loja.resources.exception.CampoDeMensagem;
import com.leocaliban.loja.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNovoDTO> {

	@Autowired
	private ClienteRepository repository;
	
	@Override
	public void initialize(ClienteInsert constraintAnnotation) {
	}

	@Override
	public boolean isValid(ClienteNovoDTO objDto, ConstraintValidatorContext context) {
		//Lista para salvar os erros personalizados
		List<CampoDeMensagem> list = new ArrayList<>();
		
		if (objDto.getTipo().equals(TipoCliente.FISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new CampoDeMensagem("cpfOuCnpj", "CPF inválido"));
		}
		
		if (objDto.getTipo().equals(TipoCliente.JURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new CampoDeMensagem("cpfOuCnpj", "CNPJ inválido"));
		}
		
		Cliente aux = repository.findByEmail(objDto.getEmail());
		if (aux != null) {
			list.add(new CampoDeMensagem("email", "Este email já está cadastrado"));
		}
		
		//Envia os erros personalizados para a lista de erros do framework que será capturada no ResourceExceptionHandler
		for(CampoDeMensagem e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMensagem()).addPropertyNode(e.getNomeCampo()).addConstraintViolation();
		}
		return list.isEmpty();
	}

}
