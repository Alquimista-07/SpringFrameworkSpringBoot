package com.bolsadeideas.springboot.app.models.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

// NOTA: La documentación oficial la encontramos referente a la implementación, métodos y demás bondades que nos
//       brinda la interface CrudRepository propia de Springboot y que nos permite implementar el CRUD de una manera
//       más sencilla la encontramos en el siguiente enlace: 
//
//       https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories
//

import com.bolsadeideas.springboot.app.models.entity.Cliente;

// NOTA: Para usar la interface CrudRepository heredamos de esta y le pasamos como primer valor
//       el tipo de dato (en este caso Cliente) y como segundo valor el tipo del id nuestra llave 
//       (en este caso Long ya que así lo difinimos en la clase Cliente).
//        Y a pesar de que acá no estamos anotando con @Component, @Repository como teníamos anteriormente antes de hacer el
//        refactor para implementar la interface propia de CrudRepository y no tenemos ninguna otra anotación, nos damos cuenta
//        que esta clase la podemos inyectar con el @Autowired como estamos haciendo en el ClienteServiceImpl. Y esto es porque
//        es una interface especial que hereda de CrudRepository y por debajo ya es un componene spring por lo tanto no es necesario
//        registrarla. Adicionalmente para la interface PagingAndSortingRepository que sirve para la paginación le pasamos la clase
//        y el tipo de la llave
public interface IClienteDao extends CrudRepository<Cliente, Long>, PagingAndSortingRepository<Cliente, Long> {
	
	
	
}
