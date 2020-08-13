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
import com.maykon.cursomc.repositories.PagamentoRepository;
import com.maykon.cursomc.repositories.PedidoRepository;
import com.maykon.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

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
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
		
		
		
	}

	@Override
	public void run(String... args) throws Exception {
		
			
		Categoria cat1 = new Categoria(null,"Informatica");
		Categoria cat2 = new Categoria(null,"Escritorio");
		Categoria cat3 = new Categoria(null,"Eletronico");
		
		
		
		Produto p1 = new Produto(null,"Computador",2000.00);
		Produto p2 = new Produto(null,"Impressora",800.00);
		Produto p3 = new Produto(null,"Mouse",80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		Estado est1 = new Estado(null,"Minas Gerais");
		Estado est2 = new Estado(null,"São Paulo");
		
		Cidade cid1 = new Cidade(null,"Uberlandia",est1);
		Cidade cid2 = new Cidade(null,"São Paulo",est2);
		Cidade cid3 = new Cidade(null,"Campinas",est2);
		Cidade cid4 = new Cidade(null,"Guarulhos",est2);
		
		//est1.getCidades().addAll(Arrays.asList(cid1));
		//est2.getCidades().addAll(Arrays.asList(cid2,cid3,cid4));
		
		
		catrepo.saveAll(Arrays.asList(cat1,cat2,cat3));
		prodrepo.saveAll(Arrays.asList(p1,p2,p3));
		
		estrepo.saveAll(Arrays.asList(est1,est2));
		cidrepo.saveAll(Arrays.asList(cid1,cid2,cid3,cid4));
		
		Cliente cli1 = new Cliente(null,"Maria Silva","maria@gmail.com","363789812377",TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("27363323","93838393"));
		
		Endereco e1 = new Endereco(null,"Rua Flores","300","apto 300","Jardim","38220834",cli1,cid1);
		Endereco e2 = new Endereco(null,"Avenida Matos","105","Sala 800","Centro","38777012",cli1,cid2);
		
		Pedido ped1 = new Pedido(null,sdf.parse("30/09/2020 10:32"),cli1,e1);
		Pedido ped2 = new Pedido(null,sdf.parse("30/09/2020 10:10"),cli1,e2);
		
		Pagamento pag1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		
		ped1.setPagamento(pag1);
		
		Pagamento pag2 = new PagamentoComBoleto(null,EstadoPagamento.PENDENTE,ped2,sdf.parse("20/10/2020 00:00"),null);
		ped2.setPagamento(pag2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		//clirepo.saveAll(Arrays.asList(cli1));
		//cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		clirepo.saveAll(Arrays.asList(cli1));
		endrepo.saveAll(Arrays.asList(e1,e2));
		
		pedrepo.saveAll(Arrays.asList(ped1,ped2));
		pagrepo.saveAll(Arrays.asList(pag1,pag2));
		
		
		
	}

}
