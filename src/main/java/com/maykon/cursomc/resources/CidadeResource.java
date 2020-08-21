package com.maykon.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maykon.cursomc.domain.Cidade;
import com.maykon.cursomc.services.CidadeService;

@RestController
@RequestMapping(value="/cidades")
public class CidadeResource {

	@Autowired
	private CidadeService service;
	
	
	@RequestMapping(value ="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		
		Cidade obj = service.buscar(id);
		return ResponseEntity.ok().body(obj);

	}
	/*
	@RequestMapping(value="/email", method=RequestMethod.GET)
	public ResponseEntity<Cidade> findAll(@RequestParam(value="value") Integer estado) {
		Cidade obj = service.(estado);
		return ResponseEntity.ok().body(obj);
	}
	*/	
}
