package com.rique.minhasfinancas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableWebMvc
public class MinhasfinancasApplication implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("http://localhost:3000").allowedMethods("GET", "POST", "PUT",
				"DELETE", "OPTIONS");
	}

	public static void main(String[] args) {
		SpringApplication.run(MinhasfinancasApplication.class, args);
	}

}

/*
 * @Component //@Profile("production") public class MensagensService {
 * 
 * @Value("${application.name}") private String appName; }
 */