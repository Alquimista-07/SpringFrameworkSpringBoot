package com.bolsadeideas.springboot.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

// NOTA: Esta clase entity es la que va a estar mapeada a la tabla de base de datos. Por lo tanto lo que hacemos para indicar que 
//       una clase POJO con atributos y métodos getter y setter que están mapeados a una tabla es una entidad de JPA o Hibernate
//       tenemos que decorar con el @Entity. A continuación lo siguiente que debemos hacer o no ya que es opcional ya que se puede 
//       omitir es anotar con @Table y esto es si queremos que nuestra clase entity se llame igual que en la tabla de base de datos.

// NOTA: Automáticamente una clase que esta anotada con el @Entity de persistencia todos los demás atributos o campos de la clase 
//       que no se anotan (Ya que como nos damos cuenta más adelante el único campo anotado con @Id y @GeneratedValue es el id)
//       se van a mapear de forma automática, sin configurar nada, sin anotar, pero por defecto va a mapear a un campo que se llama
//       exáctamente igual en la base de datos, por ejemplo la tabla clientes va a tener un llave autoincremental llamada id, va a 
//       tener un campo nombre que se va a llamar exáctamente igual, un campo nombre que también se llama igual y así sucesivamente.

@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

	// Implementamos la interface Serializable y agregamos un serialVersionUID
	// por defecto. Adicionalmente es recomenado hacer esta implementación ya
	// que muchas veces se trabaja con serialización que es el proceso de convertir
	// un objeto en una secuencia de bites, para almacenarlo o transmitirlo a la
	// memoria por ejemplo o a una base de datos o un JSON o xml, o cuando se
	// trabaja
	// con sesiones HTTP
	private static final long serialVersionUID = 1L;

	// -------------------------------
	// Atributos
	// -------------------------------
	// NOTA: Con la anotación @Id indicamos que este atributo es la llave primaria en la base de datos.
	//       Con la anotación @GeneratedValue indicamos cual es la estrategia en que se genera la llave 
	//       en la base de datos, por ejemplo autoincremental
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// NOTA: Anteriormente se mencionó que si no anotabamos el campo este se debe llamar exáctamente igual
	//       que en la tabla de base de datos, pero esto también lo podemos customizar ya que puede ser que
	//       un atributo de la clase se llama distinto a como se llama en la tabla de base de datos, por lo 
	//       tanto para ello usamos la anotación @Column para hacer referencia al nombre del atributo en la 
	//       base de datos. Adicionalmente podemos indicar la longitud del varchar en la base de datos usando 
	//       el atributo length y también customizar si acepta valores nulos usando el atributo nullable,
	//       también si el campo es único.
	//
	//       Ejemplo: @Column(name = "nombre_cliente", length = 30, unique = true)
	//
	private String nombre;

	private String apellido;
	private String email;

	@Column(name = "create_at")
	// NOTA: Esta anotación @Temporal solo se usa para fechas e indica el formato en el cual se va a 
	//       guardar la fecha en la tabla de la base de datos. Y para esto tenemos el DATE (Solo fecha),
	//       el TIME (solo la hora) y el TIMESTAMP (Fecha y hora).
	@Temporal(TemporalType.DATE)
	// NOTA: Con @DateTimeFormat especificamos el formato de la fecha
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createAt;
	
	
	//---------------------------------------------------------------------------------------------------------------------
	// NOTA: Este método lo comentamos ya que agregamos un campo adicional en el formulario para ingresar la fecha 
	//       y no generarla por sistema como estabamos haciendo.
	
	// Método para cargar la fecha antes de guardar en la base de datos
	// Y para que sea gestionado y llamado por el entity manager y se 
	// ejecute como un evento antes de guardar en la base de datos es
	// necesario decorar con el @PrePersist
	/*
	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}
	*/
	//---------------------------------------------------------------------------------------------------------------------

	// -------------------------------
	// Getters y Setters
	// -------------------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
