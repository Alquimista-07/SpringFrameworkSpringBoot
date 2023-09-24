package com.bolsadeideas.springboot.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bolsadeideas.springboot.app.models.entity.Producto;

public interface IProductoDao extends CrudRepository<Producto, Long> {
	
	// Necesitamos un método que realice una consulta pero a través de un parmámetro
	// que es el termino (term del lado de JQuery) y tiene que realizar una consulta
	// where like para que el termino coindica con productos.
	// En este caso como es una interfaz escribimos el método sin implementación para
	// que sea creado cuando implementemos esta interface donde la necesitamos y con 
	// anotaciones le pasamos la consulta.
	@Query("select p from Producto p where p.nombre like %?1%")
	public List<Producto> findByName( String term );

}
