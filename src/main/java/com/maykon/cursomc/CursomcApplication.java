package com.maykon.cursomc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	/*
	@Autowired
	private CategoriaRepository catrepo;
	
	@Autowired
	private ProdutoRepository prodrepo;
	@Autowired
	private CidadeRepository cidrepo;
	@Autowired
	private EstadoRepository estrepo;
	@Autowired
	private ClienteRepository clirepo;
	@Autowired
	private EnderecoRepository endrepo;

	@Autowired
	private PagamentoRepository pagrepo;
	
	@Autowired
	private PedidoRepository pedrepo;
	
	@Autowired
	private ItemPedidoRepository pedirepo;
	*/
	
	//@Autowired
	//private S3Service s3Service;
	
	//private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {

		//s3Service.uploadFile("C:\\temp\\teste.jpg");
		
		
	}

}
