package com.bolsadeideas.springboot.app.models.dao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.app.models.dao.IClienteDao;
import com.bolsadeideas.springboot.app.models.entity.Cliente;

// NOTA: Una clase service esta basada en el patrón de diseño fachada, el cual es un único punto de acceso hacia 
//       distitos DAO o repository y dentro de una clase servicio podríamos tener como atributo y podríamos operar
//       con distintas clases DAO y no necesitamos que acceder de forma directa a los DAO dento de los controladore
@Service("clienteServiceJPA")
public class ClienteServiceImpl implements IClienteService{

	// NOTA: Entonces la idea en la clase service es que por cada método en la clase DAO vamos a tener métodos en 
	//       el Cliente Service. Otra característica es que podemos manejar las transacciones sin tener que anotar
	//       con el @Transactional en el DAO como lo teníamos antes del refactor para implementar la clase servicio
	
	@Autowired
	private IClienteDao clienteDao;
	
	@Override
	// NOTA: Esta anotación @Transactional importada del paquete de spring marcamos el método como transaccional y le indicamos que es solamente de
	//       lectura, ya que como tal solo es una consulta. Pero cuando es un insert es decir un persist, el parámetro readOnly lo podemos omitir
	//       ya que por obvias rezones no es solo de lectura sino también de escritura
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		// NOTA: Casteamos a un list de cliente ya que el findAll() de la interface CrudRepository retorna un iterable.
		//       y esto para no tener que cambiar el método que previamente teníamos ya implementado.
		return (List<Cliente>) clienteDao.findAll();
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		clienteDao.save(cliente);		
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findOne(Long id) {
		// NOTA: Cambiamos el findOne que teníamos previamente y que creamos nosotros mismos por un método propio de la interface
		//       CrudRepository, y el cual recibe el id y retorna un Optional de cliente. Lo que hace un Optional básicamente es
		//       envolver el resultado de la consulta con sus propios métodos, como get(), orElse(), entre otros.
		//       En ese caso usmao el orElse, que básicamente indica que si lo encuentra lo retorna y si no retorna un null.
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// NOTA: Acá aplica lo mismo que el anterior método en el cual cambiamos el método que teniamos propio por el de la interface
		//       CrudRepository
		clienteDao.deleteById(id);
	}

	
}
