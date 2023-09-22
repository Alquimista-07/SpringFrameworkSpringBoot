package com.bolsadeideas.springboot.app.view.xml;

import java.util.List;

import com.bolsadeideas.springboot.app.models.entity.Cliente;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

// Clase Wrapper(envoltura) la cual va a tener un conjuento de atributos que en el fondo es lo que vamos a
// convertir en XML. Adicionalmente tenemos que indicar con una anotación que esta es la clase que contiene
// la lista de clientes y le podemos dar un nombre por parámetro, que si no lo asignamos va a tomar por
// defecto el nombre de la clase
@XmlRootElement(name = "clientes")
public class ClienteList {
	
	// Atributos
	// Indicamos con la anotacióncual es el atributo que va a ser un elemento XML por cada uno
	// y adicionalmente también le podemos indicar el nombre por parámetro
	@XmlElement(name = "cliente")
	public List<Cliente> clientes;

	// Constructores
	public ClienteList() {}
	
	public ClienteList(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	// Getters
	public List<Cliente> getClientes() {
		return clientes;
	}
	

}
