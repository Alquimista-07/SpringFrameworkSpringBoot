package com.bolsadeideas.springboot.di.app.models.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;


@Component
/*
 * NOTA: Como tenemos dos clases que implementan la misma interface, es decir, son dos candidatos posibles,
 *       para la misma interface y por lo tanto nos va a causar un error, por lo tanto tenemos dos caminos
 *       o dos posibles soluciones, para la cual la pirmera es usar la anotación o decorador @Primary y va 
 *       a indicar que esta clase va a ser la por defecto, por lo tanto tenemos que anotar con dicho decorador
 *       la que queremos que se inyecte por defecto. Y esto es bien potente ya que nos permite desacoplar 
 *       completamente la implementación concreta de las clases que esta usando el componente que en este caso
 *       este servicio. Y si queremos hacer el switch de una clase a otra simplemente es cambiar el decorador
 *       @Primary a la clase que queremos que sea tomada.
 */
@Primary
public class MiServicioComplejo implements IServicio2 {
	
	@Override
	public String operacion() {
		return "Ejecutando algún proceso importante complicado...";
	}

}
