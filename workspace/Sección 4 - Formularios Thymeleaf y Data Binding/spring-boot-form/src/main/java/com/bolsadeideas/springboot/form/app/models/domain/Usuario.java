package com.bolsadeideas.springboot.form.app.models.domain;

import java.util.Date;

//import org.springframework.format.annotation.DateTimeFormat;

import com.bolsadeideas.springboot.form.app.validation.IdentificadorRegex;
import com.bolsadeideas.springboot.form.app.validation.Requerido;

import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
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
	// NOTA: Ahora como creamos nuestra propia anotación de validación lo que vamos a hacer es usarla
	@IdentificadorRegex
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
	
	/*
	 * NOTA: Comentamos la anotación para usar una anotación personalizada creada por nosotros mismos.
	 *       Adicionalmente también cambiamos el mensaje ya sea de la siguiente forma:
	 *       
	 *       @Requerido(message = "Campo requerido")
	 *       
	 *       O también lo cambiamos a través del messages.properties ya que nuestra anotación personalizada
	 *       la creamos con un mensaje un poco más genérico para que sirva para validar otros campos.
	 *       
	 */
	//@NotEmpty
	@Requerido
	private String apellido;
	
	/*
	 * NOTA: La anotación @NotBlank evita que se acepten espacioes en blanco y
	 *       que pase la validación. Adiciona
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

	@NotNull
	@Min(5)
	@Max(5000)
	private Integer cuenta;
	
	@NotNull
	// NOTA: La anotación @Past valida que tiene que ser una fecha pasada a la actual y no una futura
	//       también existe la @PastOrPresent
	@Past
	// NOTA: La antoación @Future valida que tiene que ser una fecha futura y no una pasada o presente
	//       también existe la @FutureOrPresent
	//@Future
	// NOTA: Comentamos el date format para formatear fechas con @InitBinder y registrar un CustomDateEditor
	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaNacimiento;
	
	@NotEmpty
	private String pais;

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

	public Integer getCuenta() {
		return cuenta;
	}

	public void setCuenta(Integer cuenta) {
		this.cuenta = cuenta;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

}
