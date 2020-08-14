package com.maykon.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.maykon.cursomc.domain.Cliente;
import com.maykon.cursomc.domain.enums.TipoCliente;
import com.maykon.cursomc.dto.ClienteNewDTO;
import com.maykon.cursomc.repositories.ClienteRepository;
import com.maykon.cursomc.resources.exceptions.FieldMessage;
import com.maykon.cursomc.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	
	@Autowired
	private ClienteRepository repo;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}
	
	

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		
		
		
		List<FieldMessage> list = new ArrayList<>();
		
		if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cnpjOuCnpj","CPF Invalido"));
		}
		if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA .getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cnpjOuCnpj","CNPJ Invalido"));
		}
		
		Cliente aux = repo.findByEmail(objDto.getEmail());
		
		if (aux!= null) {
			list.add(new FieldMessage("email","Email Já existente"));
		}
		
		// inclua os testes aqui, inserindo erros na lista
		// Aqui cria uma lista de erro no FrameWork
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
