package com.bolsadeideas.springboot.form.app.models.domain;

import jakarta.validation.constraints.NotEmpty;

/*
 * NOTA: Esta es una clase POJO, una clase Entity que representa datos
 */
public class Usuario {

	//
	/*
	 * NOTA: Muy importante que estos nombre de atributos correspondan al mismo
	 * nombre del campo que se definio en el atributo name del formulario
	 */
	/*
	 * NOTA: Acá indicamos la regla de validación con anotaciones como por 
	 *       ejemplo @NotEmpty que es para campos requeridos
	 */
	@NotEmpty
	private String username;
	
	@NotEmpty
	private String pwd;
	
	@NotEmpty
	private String email;

	// Getters y Setters
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
