package com.bolsadeideas.springboot.web.app.models;

/*
 * NOTA: Acá vamos a crear un POJO que es una clase simple que contiene atributos
 *       para sus datos, en este caso Usuario que contiene nombre, apellido, email,
 *       etc, y sus respectivos métostt Getters para poder acceder al atributo y su
 *       valor y Setters para poder modificarlos. También este POJO puede implementar
 *       interfaces como el serializable, interfaces propias de JAVA pero no de un 
 *       Framework como Spring o Hibernate o alguno que acople esta clase ya que la 
 *       idea es que sea completamente desacoplada de cualquier Framework o API.
 *       También que no herede ni implemente alguna interface externa.
 */
public class Usuario {
	
	// Atributos
	private String nombre;
	private String apellido;
	private String email;
	
	// Constructores
	public Usuario() {
	}
	
	public Usuario(String nombre, String apellido, String email) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
	}
	
	
	// Getters y Setters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getApellido() {
		return apellido;
	}
	
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
