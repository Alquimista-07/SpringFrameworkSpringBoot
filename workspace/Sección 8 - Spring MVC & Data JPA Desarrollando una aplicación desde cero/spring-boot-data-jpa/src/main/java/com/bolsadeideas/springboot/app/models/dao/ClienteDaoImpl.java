package com.bolsadeideas.springboot.app.models.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bolsadeideas.springboot.app.models.entity.Cliente;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

// NOTA: Esta clase es un objeto de acceso a datos (en inglés: data access object, abreviado DAO) es un componente de software que suministra 
//       una interfaz común entre la aplicación y uno o más dispositivos de almacenamiento de datos, tales como una Base de datos o un archivo

// NOTA: La anotación @Repository se usa para marcar la clase como un componente de persitencia de acceso a datos (DAO).
//       Adicionalmente podemos indicar el nombre como parámetro entre comillas para poder usarlo con la anotación @Qualifier
//       cuando queramos inyectar
@Repository
public class ClienteDaoImpl implements IClienteDao {

	// NOTA: Creamos el atributo entity manager el cual se encarga de manejar las clases que son entidades (que son anotadas con @Entity), su 
	//       ciclo de vida, las persiste dentro del contexto, las actualiza, las elimina, puede realizar consultas, es decir todas la operaciones
	//       a la base de datos pero a nivel de objeto a través de las clases entity por lo tanto las consultas son siempre consultas de JPA, es
	//       decir, son consultas que van a la clase entity y no a la tabla de base de datos ya que recordemos que la clase esta mapeada a la tabla.
	//       
	//       Adicionalmente es necesario anotarlo con @PersistenceContext que contiene la unidad de persistencia, por lo tanto el va a inyectar el 
	//       entity manager según la configuración de la unidad de persistencia que contiene el datasource que contiene el proveedor de JPA, y por 
	//       defecto si no configuramos ningún tipo de base de datos, ningún tipo de motor en el application.properties de forma automática Spring Boot
	//       va a utilizar la base de datos H2 embebida en la aplicación y tenemos que tener por lo tanto la dependecia agregada y que la colocamos cuando
	//       creamos el proyecto, pero esto se puede a cambiar más adelante por una BD MySQL o PostgreSQL u Oracle, etc.
	@PersistenceContext
	private EntityManager em;
	
	// Suprimimos los warnings
	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> findAll() {
		// NOTA: Acá en el return de este método creamos una consulta usando el método createQuery, el cual recibe como parámetro
		//       un from y el nombre de la clase que en este caso es Cliente
		return em.createQuery("from Cliente").getResultList();
	}

	@Override
	public void save(Cliente cliente) {
		// Validamos para saber si es un cliente nuevo o uno que vamos a editar
		if (cliente.getId() != null && cliente.getId() > 0) {
			// Editamos el cliente usando un método propio del entity manager
			em.merge(cliente);
		} else {	
			// Si no indica que es un cliente nuevo por lo tanto guardamos en la base de datos usando
			// el persist que es un método propio del entity manager
			em.persist(cliente);		
		}
		
	}

	@Override
	public Cliente findOne(Long id) {
		// Buscamos un cliente por id usando un método propio del entity manager
		return em.find(Cliente.class, id);
	}

	@Override
	public void delete(Long id) {
		// Obtenemos el cliente a eliminar usando el méotodo findOne previamente impementado
		// y se lo pasamos al método propio del entity manager que sirve para eliminar
		em.remove(findOne(id));
	}

}
