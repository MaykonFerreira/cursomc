package com.maykon.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.maykon.cursomc.domain.Categoria;
import com.maykon.cursomc.domain.Cidade;
import com.maykon.cursomc.domain.Estado;
import com.maykon.cursomc.domain.Produto;
import com.maykon.cursomc.repositories.CategoriaRepository;
import com.maykon.cursomc.repositories.CidadeRepository;
import com.maykon.cursomc.repositories.EstadoRepository;
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
		
		
	}

}
