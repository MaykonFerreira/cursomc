package com.maykon.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maykon.cursomc.domain.Estado;
import com.maykon.cursomc.repositories.EstadoRepository;
import com.maykon.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository repo;

	
	public Estado buscar(Integer id) {

		Optional<Estado> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Estado: " + Estado.class.getName()));		

	}
	public List<Estado> findAll(){
		return repo.findAllByOrderByNome();
	}

}
