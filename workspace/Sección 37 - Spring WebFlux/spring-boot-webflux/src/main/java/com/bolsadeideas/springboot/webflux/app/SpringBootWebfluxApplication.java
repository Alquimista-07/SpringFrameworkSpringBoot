package com.bolsadeideas.springboot.webflux.app;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.bolsadeideas.springboot.webflux.app.models.dao.ProductoDao;
import com.bolsadeideas.springboot.webflux.app.models.documents.Producto;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootWebfluxApplication implements CommandLineRunner {
	
	@Autowired
	private ProductoDao dao;
	
	// Este atributo que creamos del tipo ReactiveMongoTemplate nos permite para hacer algo similar a lo que teníamos en JPA
	// que cada vez que arrancamos o bajamos la aplicación los datos de prueba que tenemos se van a borrar de forma automática.
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	
	final Logger log = LoggerFactory.getLogger(SpringBootWebfluxApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebfluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		// Borramos los datos de prueba de forma automática. OJO esto es solo para desarrollo
		mongoTemplate.dropCollection("productos").subscribe();
		
		// Creamos un flujo de productos
		Flux.just(new Producto("TV Panasonic Pantalla LCD", 456.89),
				  new Producto("Sony Camara HD Digital", 177.89),
				  new Producto("Apple iPod", 46.89),
				  new Producto("Sony Notebook", 846.89),
				  new Producto("Hewlett Packard Multifuncional", 200.89),
				  new Producto("Bianchi Bicicleta", 70.89),
				  new Producto("HP Notebook Omen 17", 2500.89),
				  new Producto("Mica Cómoda 5 Cajones", 150.89),
				  new Producto("TV Sony Bravia OLED 4K Ultra HD", 2255.89)
				  )
		// El flatMap hace lo mismo que el Map con la diferencia de que el flatMap esta preparado para manejar y trabajar con tipos observable como Flux o Mono
		// que es lo que nos regresa el método save
		.flatMap(producto -> {
			// Agregamos la fecha
			producto.setCreateAt(new Date());
			// Guardamos los datos
			return dao.save(producto);
			})
		.subscribe(producto -> log.info("Insert: " + producto.getId() + " " + producto.getNombre()));
		
	}

}
