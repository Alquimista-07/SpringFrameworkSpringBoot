package com.bolsadeideas.springboot.di.factura.app.models.domain;

public class Producto {

	// Atributos
	private String nombre;

	private Integer precio;

	// Constructor
	public Producto(String nombre, Integer precio) {
		this.nombre = nombre;
		this.precio = precio;
	}

	// Getters y Setters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getPrecio() {
		return precio;
	}

	public void setPrecio(Integer precio) {
		this.precio = precio;
	}

}
