package com.bolsadeideas.springboot.form.app.models.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/*
 * NOTA: Esta es una clase POJO, una clase Entity que representa datos
 */
public class Usuario {
	
	// NOTA: Este campo no va a estar en el formulario sino que va a ser un dato interno
	//       que si va a tener información
	private String identificador;
	
	// Atributo para mostrar como funcionan las validaciones con expresiones regulares
	// El \\d es equivalente a [0-9]
	/*
	 * NOTA: Comentamos el @Pattern para hacer validaciones a través de una clase personalizada a la cual se le implmenta
	 *       la interface Validator del paquete org.springframework.validation.Validator
	 *       
	 * @Pattern(regexp = "[0-9]{2}[.][\\d]{3}[.][\\d]{3}[-][A-Z]{1}")
	 */
	private String idRegex;

	//
	/*
	 * NOTA: Muy importante que estos nombre de atributos correspondan al mismo
	 * nombre del campo que se definio en el atributo name del formulario
	 */
	/*
	 * NOTA: Acá indicamos la regla de validación con anotaciones como por 
	 *       ejemplo @NotEmpty que es para campos requeridos
	 */	
	// Este permite validar el nombre y adicionalmente con la propiedad message asingar un mensaje personalizado
	/*
	 *  NOTA: Comentamos esto para hacer validaciones a través de una clase personalizada a la cual se le implmenta
	 *       la interface Validator del paquete org.springframework.validation.Validator
	 * 
	 * @NotEmpty(message = "El nombre no puede estar vacío")
	 */
	private String nombre;
	
	@NotEmpty
	private String apellido;
	
	/*
	 * NOTA: La anotación @NotBlank evita que se acepten espacioes en blanco y
	 *       que pase la validación.
	 */
	@NotBlank
	@Size(min = 3, max = 8)
	private String username;
	
	@NotEmpty
	private String pwd;
	
	@NotEmpty
	// Este permite validar el correo y adicionalmente con la propiedad message asingar un mensaje personalizado
	@Email(message = "Correo con formato incorrecto")
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

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	
	public String getIdRegex() {
		return idRegex;
	}
	
	public void setIdRegex(String idRegex) {
		this.idRegex = idRegex;
	}

}
