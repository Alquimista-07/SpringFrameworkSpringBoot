package com.bolsadeideas.springboot.backend.apirest.models.services;

import java.util.List;

import com.bolsadeideas.springboot.backend.apirest.models.entity.Cliente;

// Esta interface contiene el contrato de implementación, es decir, los métodos del CRUD
public interface IClienteService {

	// Listar todo
	public List<Cliente> findAll();
	
}
