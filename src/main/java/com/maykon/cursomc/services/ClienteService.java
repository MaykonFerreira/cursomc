package com.maykon.cursomc.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.licensemanager.model.AuthorizationException;
import com.maykon.cursomc.domain.Cidade;
import com.maykon.cursomc.domain.Cliente;
import com.maykon.cursomc.domain.Endereco;
import com.maykon.cursomc.domain.enums.Perfil;
import com.maykon.cursomc.domain.enums.TipoCliente;
import com.maykon.cursomc.dto.ClienteDTO;
import com.maykon.cursomc.dto.ClienteNewDTO;
import com.maykon.cursomc.repositories.CidadeRepository;
import com.maykon.cursomc.repositories.ClienteRepository;
import com.maykon.cursomc.repositories.EnderecoRepository;
import com.maykon.cursomc.security.UserSS;
import com.maykon.cursomc.services.exceptions.DataIntegrityException;
import com.maykon.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private ClienteRepository repo;
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private CidadeRepository repocid;
	@Autowired
	private EnderecoRepository repoend;
	
	
	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private ImageService imageService;
	
	
	@Value("${img.prefix.client.profile}")
	private String prefix;
	
	@Value("${img.profile.size}")
	private Integer size;
	
	public Cliente buscar(Integer id) {
		
		// Controla o acesso de acordo com o perfil
		UserSS user = UserService.authenticated();
		if(user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		//Optional<Categoria> obj = repo.findById(id);
		//return obj.orElse(null);
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));		

	}

	@Transactional
	public Cliente inserir(Cliente obj) {
		

		obj.setId(null);
		obj = repo.save(obj);
		repoend.saveAll(obj.getEnderecos());
		return obj;
	}
	@Transactional
	public Cliente inserirx(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		repoend.saveAll(obj.getEnderecos());
		return obj;
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
			throw new DataIntegrityException("Não é possivel excluir porque a há pedidos relacionados");
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
	
		return new Cliente(objDto.getId(),objDto.getNome(),objDto.getEmail(),null,null,null);
		//throw new UnsupportedAddressTypeException();
	}	
	
	public Cliente fromDTO(ClienteNewDTO objDto) {
		
		Cliente cli = new Cliente(null,objDto.getNome(),objDto.getEmail(),objDto.getCpfOuCnpj(),TipoCliente.toEnum(objDto.getTipo()),pe.encode(objDto.getSenha()));
		Cidade cid = repocid.findById(objDto.getCidadeId()).get();
		Endereco end = new Endereco(null,objDto.getLogradouro(),objDto.getNumero(),objDto.getComplemento() , objDto.getBairro(),objDto.getCep(),cli, cid);
		
		System.out.println(end.getLogradouro());
		cli.setEnderecos(Arrays.asList(end));
		cli.getTelefones().add(objDto.getTelefone1());
		if(objDto.getTelefone2()!= null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if(objDto.getTelefone3()!= null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
		
	}	
	
	
	public URI uploadProfilePicture(MultipartFile multipartFile) {
	
		UserSS user = UserService.authenticated();
		
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		/*  aqui gravava o URL da IMAGEM
		URI uri = s3Service.uploadFile(multipartFile);
		Cliente cli = clienteService.buscar(user.getId());
		cli.setImageUrl(uri.toString());
		repo.saveAll(Arrays.asList(cli));
		return uri;
		*/
		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);
		String fileName = prefix + user.getId()+".jpg";
		
		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
		
		/*
		
		
		String fileName = prefix + user.getId() + ".jpg";
		
		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
		*/
	}
	 
	//public URI uploadProfilePicture(MultipartFile multipartFile) {
		//return s3Service.uploadFile(multipartFile);
	//} 

	
}
