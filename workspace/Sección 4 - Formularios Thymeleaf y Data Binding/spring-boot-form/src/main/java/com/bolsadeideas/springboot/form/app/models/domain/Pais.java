package com.bolsadeideas.springboot.form.app.models.domain;

//import jakarta.validation.constraints.NotNull;

public class Pais {

	// Atributos
	// Como estamos validando por el property editor del objeto esta validación con la
	// anotación @NotNull ya no es necesaria.
	//@NotNull
	private Integer id;
	
	private String codigo;
	
	private String nombre;

	// Constructor
	public Pais() {

	}

	public Pais(Integer id, String codigo, String nombre) {
		this.id = id;
		this.codigo = codigo;
		this.nombre = nombre;
	}

	// Getters y Setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return this.id.toString();
	}

}
