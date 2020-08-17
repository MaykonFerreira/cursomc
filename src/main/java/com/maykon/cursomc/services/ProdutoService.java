package com.maykon.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.maykon.cursomc.domain.Categoria;
import com.maykon.cursomc.domain.Produto;
import com.maykon.cursomc.repositories.CategoriaRepository;
import com.maykon.cursomc.repositories.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repo;

	@Autowired
	private CategoriaRepository repocat;
	
	public Produto buscar(Integer id) {
		Optional<Produto> obj = repo.findById(id);
		return obj.orElse(null);

	}

	public Page<Produto> search(String nome, List<Integer> ids,Integer page, Integer linesPerPage,String orderBy,String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction) ,orderBy);
		List<Categoria> categorias = repocat.findAllById(ids);
		//return repo.search(nome,categorias,pageRequest);
		
		// Consulta pronta do Spring Data
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome,categorias,pageRequest);
		
	}
	
	
}
