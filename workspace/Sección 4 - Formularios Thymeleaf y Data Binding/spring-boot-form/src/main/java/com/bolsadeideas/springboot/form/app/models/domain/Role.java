package com.bolsadeideas.springboot.form.app.models.domain;

public class Role {

	// Atributos
	private Integer id;
	private String nombre;
	private String role;

	public Role() {

	}

	public Role(Integer id, String nombre, String role) {
		this.id = id;
		this.nombre = nombre;
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public boolean equals(Object obj) {
		
		if( this == obj ) {
			return true;
		}
		
		if(!(obj instanceof Role)) {
			return false;
		}
		
		// Si es una instancia de role hacemos
		Role role = (Role) obj;
		// Comparamos cada objeto con la lista de los usuarios para marcar el role por defecto
		return this.id != null && this.id.equals(role.getId());
	}

}
