package com.bolsadeideas.springboot.app;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// Pamos al builder la clase principal de spring boot que sabemos que es la que esta 
		// anotada con @SpringBootApplication
		return builder.sources(SpringBootDataJpaApplication.class);
	}

}
