package com.bolsadeideas.springboot.di.app.models.service;

/*
 * NOTA: Esta es una clase que se encarga de trabaj con los datos, de operar con lo que es lógica de negocio.
 *       Por lo general un servicio es una fachada de acceso a los objetos DAO, por ejemplo que realizan
 *       consultas, operaciones en las tablas de base de datos, independiente si se esta utilizando jdbc o 
 *       algún ORM (Hibernate con JPA) pero que acceden a los datos, realizan consultas, operaciones para
 *       implementar un CRUD, pero un service podría tener un atributo de un DAO o varios atributos y estos DAO
 *       podrían interactuar bajo una misma transacción en un método.
 */

/*
 * NOTA: En esta clase se va a partir sin usar inyección de dependencias para observar la diferencia y ¿cómo se acopla
 *       esta clase service cuando no se usa la inyección de dependencias, ni tampoco interfaces, y de como se acopla 
 *       al controlador. Entonces por el momento como no se va a usar inyección de dependencias no lo vamos a anotar, 
 *       es decir, simplemente es una clase con un método
 */
public class MiServicio {
	
	public String operacion() {
		return "Ejecutando algún proceso importante...";
	}

}
