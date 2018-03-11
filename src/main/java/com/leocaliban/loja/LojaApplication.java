package com.leocaliban.loja;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LojaApplication implements CommandLineRunner{

	
	public static void main(String[] args) {
		SpringApplication.run(LojaApplication.class, args);
	}

	//Através do CommandLineRunner, por padrão serão instanciados esses objetos ao iniciar a aplicação
	@Override
	public void run(String... arg0) throws Exception {
		

	}
}
