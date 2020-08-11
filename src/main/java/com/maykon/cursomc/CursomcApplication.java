package com.maykon.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.maykon.cursomc.domain.Categoria;
import com.maykon.cursomc.repositories.CategoriaRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository catrepo;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
		
		
		
	}

	@Override
	public void run(String... args) throws Exception {
		
			
		Categoria cat1 = new Categoria(null,"Informatica");
		Categoria cat2 = new Categoria(null,"Escritorio");
		Categoria cat3 = new Categoria(null,"Eeltronico");
		
		catrepo.saveAll(Arrays.asList(cat1,cat2,cat3));
		
	}

}
