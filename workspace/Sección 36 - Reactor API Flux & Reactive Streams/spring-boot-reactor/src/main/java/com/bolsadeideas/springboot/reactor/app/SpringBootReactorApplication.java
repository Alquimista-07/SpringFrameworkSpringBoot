package com.bolsadeideas.springboot.reactor.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import reactor.core.publisher.Flux;

/*
 * Enlaces de documentaicón:
 *  
 *  https://projectreactor.io/docs/core/release/reference/#getting
 *  https://www.reactive-streams.org/
 *  https://github.com/reactive-streams/reactive-streams-jvm/tree/v1.0.4
 *  https://reactivex.io/
 */

// NOTA: Como la aplicación va a ser solo de consola implementamos al interface CommandLineRunner 
@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		final Logger log = LoggerFactory.getLogger(SpringBootReactorApplication.class);
		
		// Creamos el observable
		Flux<String> nombres = Flux.just("Andres", "Pedro", "Julián")
				// El evento doOnNext se ejecuta cada vez que el observador (nombres en este caso) notifica que ha llegado un elemento.
				// Adicionalmente el :: es una característica de Java 8 que permite abreviar más el código ya que en si eso sería lo mismo
				// que hacer .doOnNext(elemento -> System.out.println(elemento));
				//.doOnNext(System.out::println);
				
				// Emulamos un error
				.doOnNext(e -> {
					if(e.isEmpty()) {
						
						throw new RuntimeException("Nombres no pueden ser vacíos");
						
					}					
					
					// Si no hubo error continúa.
					System.out.println(e);
					
				});
		
		// Como ya sabemos para invocar un observable es necesario subscribirnos.
		// Adicionalmente cuando nos suscribimos podemos manejar otras cosas.
		//nombres.subscribe(log::info);
		nombres.subscribe(e -> log.info(e),
				// Como segundo elemento podemos manejar el error.
				error -> log.error(error.getMessage()));
	}

}
