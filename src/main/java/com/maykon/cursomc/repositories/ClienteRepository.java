package com.maykon.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.maykon.cursomc.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Integer>{

	// Otimiza a consulta para retorno rapido
	@Transactional(readOnly=true)
	Cliente findByEmail(String email);
	
}
