package com.bolsadeideas.springboot.backend.apirest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bolsadeideas.springboot.backend.apirest.models.entity.Cliente;
import com.bolsadeideas.springboot.backend.apirest.models.services.IClienteService;

// Como se había mencionado en secciones anteriores como esta es una aplicación solo backend con API REST 
// los controladores los anotamos con @RestController y no con @Controller.
@RestController
@RequestMapping("/api") //Ruta principal
// Damos acceso al dominio de la aplicación en Angular para que tenga acceso. Adicionalmente como segundo parámetro 
// indicando el methods podemos decirle que tipo de peticiones se pueden hacer, pero en este caso no los indicamos 
// para que por defecto se pueda hacer todo tipo de peticiones. También se pueden colocar restricciones sobre los
// header, y otras cosas que podemos buscar en internet y ver su documentación.
@CrossOrigin(origins = {"http://localhost:4200"}) 
public class ClienteRestController {
	
	// Inyectamos el servicio
	@Autowired
	private IClienteService clienteService;
	
	// Ruta para listar los clientes
	@GetMapping("/clientes")
	public List<Cliente> index() {
		return clienteService.findAll();
	}
	
	// Ruta para obtener un cliente por id
	@GetMapping("/clientes/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		Cliente cliente = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			
			cliente = clienteService.findById(id);
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			response.put("mensaje", "Error al realizar la consulta en la base de datos. Contacte con el administrador.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		if(cliente == null) {
			response.put("mensaje", "El cliente con ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	
	}
	
	// Ruta para crear un cliente.
	// Hay que tener en cuenta que como la información viene en formato JSON dentro del cuerpo de la petición, tenemos
	// que indicar que es @RequestBody, adicionalmente anotamos con @ResponseStatus para dar el status 201 de respuesta 
	// correspondiente a creado a la aplicación que realiza la petición, en este caso el frontend de Angular. También si 
	// no indicamos el @ResponseStatus por defecto va a ser ok status 200.
	@PostMapping("/clientes")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente crear(@RequestBody Cliente cliente) { 
		return clienteService.save(cliente);
	}
	
	// Ruta para actualizar un cliente
	@PutMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente actualizar(@RequestBody Cliente cliente, @PathVariable Long id) {
		
		// Obtenemos el cliente de la base de datos por su id
		Cliente clienteActual = clienteService.findById(id);
		
		// Pasamos los valores modificados
		clienteActual.setNombre(cliente.getNombre());
		clienteActual.setApellido(cliente.getApellido());
		clienteActual.setEmail(cliente.getEmail());
		
		// Persistimos el cliente con los cambios realizados. Esto internamente va a hacer un merge de los
		// datos para actualizarlos y por lo tanto esto detás de escena se traduce en un update en la base
		// de datos
		return clienteService.save(clienteActual);
		
	}
	
	// Ruta para eliminar un cliente por id
	@DeleteMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		clienteService.delete(id);
	}

}
