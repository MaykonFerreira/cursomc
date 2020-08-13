package com.maykon.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maykon.cursomc.domain.Pagamento;
import com.maykon.cursomc.repositories.PagamentoRepository;

@Service
public class PagamentoService {

	@Autowired
	private PagamentoRepository repo;

	
	public Pagamento buscar(Integer id) {
		Optional<Pagamento> obj = repo.findById(id);
		return obj.orElse(null);

	}

}
