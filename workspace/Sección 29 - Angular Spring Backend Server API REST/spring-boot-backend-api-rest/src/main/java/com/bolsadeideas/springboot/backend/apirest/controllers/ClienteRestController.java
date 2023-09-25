package com.bolsadeideas.springboot.backend.apirest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bolsadeideas.springboot.backend.apirest.models.entity.Cliente;
import com.bolsadeideas.springboot.backend.apirest.models.services.IClienteService;

// Como se había mencionado en secciones anteriores como esta es una aplicación solo backend con API REST 
// los controladores los anotamos con @RestController y no con @Controller.
@RestController
@RequestMapping("/api") //Ruta principal
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
