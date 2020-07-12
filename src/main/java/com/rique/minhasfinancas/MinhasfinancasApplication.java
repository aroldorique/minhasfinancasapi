package com.rique.minhasfinancas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MinhasfinancasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinhasfinancasApplication.class, args);
	}

}

/*
 * @Component //@Profile("production") public class MensagensService {
 * 
 * @Value("${application.name}") private String appName; }
 */