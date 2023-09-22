package com.bolsadeideas.springboot.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.bolsadeideas.springboot.app.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {

	// NOTA: Recordemos que al llamar el método de esta forma automáticamente se ejecuta la consulta
	//       JPQL: "select u from Usuario u where u.usuername=?1" pero si no llamamos el método de esta 
	//       forma sino que indicamos cualquier otro en ese caso nos tocaría anotar e indicar la consulta
	//       con el @Query("select u from Usuario u where u.username=?1")
	public Usuario findByUsername(String username);
	
}
