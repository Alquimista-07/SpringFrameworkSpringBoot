package com.bolsadeideas.springboot.app.models.dao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.app.models.dao.IClienteDao;
import com.bolsadeideas.springboot.app.models.dao.IFacturaDao;
import com.bolsadeideas.springboot.app.models.dao.IProductoDao;
import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.entity.Factura;
import com.bolsadeideas.springboot.app.models.entity.Producto;

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
	
	@Autowired
	private IProductoDao productoDao;
	
	@Autowired
	private IFacturaDao facturaDao;
	
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

	// Método para manejar la paginación
	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findAll(Pageable pageable) {
		return clienteDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findByName(String term) {
		return productoDao.findByName(term);
	}

	@Override
	@Transactional
	public void saveFactura(Factura factura) {
		facturaDao.save(factura);
	}

	@Override
	@Transactional(readOnly = true)
	public Producto findProductoById(Long id) {
		return productoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Factura findFacturaById(Long id) {
		return facturaDao.findById(id).orElse(null);
	}

	
}
