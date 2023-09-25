package com.bolsadeideas.springboot.backend.apirest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

}
