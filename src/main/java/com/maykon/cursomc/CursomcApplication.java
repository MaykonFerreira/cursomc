package com.maykon.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.maykon.cursomc.domain.Categoria;
import com.maykon.cursomc.domain.Cidade;
import com.maykon.cursomc.domain.Cliente;
import com.maykon.cursomc.domain.Endereco;
import com.maykon.cursomc.domain.Estado;
import com.maykon.cursomc.domain.ItemPedido;
import com.maykon.cursomc.domain.Pagamento;
import com.maykon.cursomc.domain.PagamentoComBoleto;
import com.maykon.cursomc.domain.PagamentoComCartao;
import com.maykon.cursomc.domain.Pedido;
import com.maykon.cursomc.domain.Produto;
import com.maykon.cursomc.domain.enums.EstadoPagamento;
import com.maykon.cursomc.domain.enums.TipoCliente;
import com.maykon.cursomc.repositories.CategoriaRepository;
import com.maykon.cursomc.repositories.CidadeRepository;
import com.maykon.cursomc.repositories.ClienteRepository;
import com.maykon.cursomc.repositories.EnderecoRepository;
import com.maykon.cursomc.repositories.EstadoRepository;
import com.maykon.cursomc.repositories.ItemPedidoRepository;
import com.maykon.cursomc.repositories.PagamentoRepository;
import com.maykon.cursomc.repositories.PedidoRepository;
import com.maykon.cursomc.repositories.ProdutoRepository;
import com.maykon.cursomc.services.S3Service;

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
	
	@Autowired
	private S3Service s3Service;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {

		s3Service.uploadFile("C:\\temp\\teste.jpg");
		
		
	}

}
