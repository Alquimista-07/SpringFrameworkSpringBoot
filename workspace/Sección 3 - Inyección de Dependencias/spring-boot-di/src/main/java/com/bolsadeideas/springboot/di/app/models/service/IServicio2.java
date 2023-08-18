package com.bolsadeideas.springboot.di.app.models.service;

/*
 * NOTA: Una interface contiene métodos sin implementar, contrátos,
 *       y dichos contratos dicen que operaciones van a implementar
 *       las clases concretas, es decir, en el fondo es el comportamiento
 *       lo que hacen y podríamos tener más de una clase concreta 
 *       que implementa la interface y todas hacen cierta tarea o trabajo
 *       pero de forma distinta implementada por ejemplo de alguna u otra 
 *       forma diferente, por ejemplo en base de datos podríamos tener una
 *       que trabaje con jdbc, con consultas nativas SQL, mientras que otra
 *       implementación trabaje con Hibernate o JPA con un ORM con consultas
 *       a nivel de objetos.
 */
public interface IServicio2 {
	
	public String operacion();

}
