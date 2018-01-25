package com.leocaliban.loja.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.leocaliban.loja.domain.Cliente;
import com.leocaliban.loja.dto.ClienteDTO;
import com.leocaliban.loja.repositories.ClienteRepository;
import com.leocaliban.loja.resources.exception.CampoDeMensagem;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

	//para obter o parametro da uri e recuperar o id do item que será atualizado
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteRepository repository;
	
	@Override
	public void initialize(ClienteUpdate constraintAnnotation) {
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Long uriId = Long.parseLong(map.get("id"));
		
		//Lista para salvar os erros personalizados
		List<CampoDeMensagem> list = new ArrayList<>();
				
		Cliente aux = repository.findByEmail(objDto.getEmail());
		if (aux != null && !aux.getId().equals(uriId)) {
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
