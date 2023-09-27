package com.bolsadeideas.springboot.webflux.app.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.bolsadeideas.springboot.webflux.app.models.documents.Producto;

public interface IProductoDao extends ReactiveMongoRepository<Producto, String> {

	// De la misma manera que con JPA cuando implementamos la interface CrudRepository
	// acá también podemos tener métodos propios personalizados que interactuen con los 
	// datos.
	
}
