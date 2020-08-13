package com.maykon.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.maykon.cursomc.domain.Cliente;
import com.maykon.cursomc.dto.ClienteDTO;
import com.maykon.cursomc.repositories.ClienteRepository;
import com.maykon.cursomc.services.exceptions.DataIntegrityException;
import com.maykon.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;

	
	public Cliente buscar(Integer id) {
		//Optional<Categoria> obj = repo.findById(id);
		//return obj.orElse(null);
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));		

	}

	public Cliente inserir(Cliente obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	public Cliente update(Cliente obj) {
		//obj.setId(null);
		Cliente newObj = buscar(obj.getId());
		updateData(newObj,obj);
		return repo.save(newObj);
	}

	private void  updateData(Cliente newObj,Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}	
	public void delete(Integer id) {
		//obj.setId(null);
		buscar(id);
		try {
			repo.deleteById(id);
		}
		
		catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir porque a há entidades relacionadas");
		}
		
	}
	public List<Cliente> findAll(){
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page,Integer linesPerPage,String orderBy,String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction) ,orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDto) {
	
		return new Cliente(objDto.getId(),objDto.getNome(),objDto.getEmail(),null,null);
		//throw new UnsupportedAddressTypeException();
	}	

	
}
