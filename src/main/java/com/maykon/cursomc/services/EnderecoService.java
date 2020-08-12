package com.maykon.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maykon.cursomc.domain.Endereco;
import com.maykon.cursomc.repositories.EnderecoRepository;
import com.maykon.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository repo;

	
	public Endereco buscar(Integer id) {
		//Optional<Categoria> obj = repo.findById(id);
		//return obj.orElse(null);
		Optional<Endereco> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Endereco.class.getName()));		

	}

}
