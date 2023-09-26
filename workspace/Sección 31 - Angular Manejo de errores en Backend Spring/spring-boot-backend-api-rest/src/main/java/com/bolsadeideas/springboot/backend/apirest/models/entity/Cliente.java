package com.bolsadeideas.springboot.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

// POJO: Cliente

@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

	// Identificador autoincremental
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// No puede ser nulo
	@Column(nullable = false)
	private String nombre;
	
	// El column no es necesario ya que el atributo se llama igual en el que en la base de datos
	private String apellido;
	
	// No puede ser nulo y además tiene que ser único
	@Column(nullable = false, unique = true)
	private String email;
	
	// Colunma mapeada a un atributo que se llama distinto en la base de datos por lo tanto se mapea con @Column
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;
	
	// Creamos un pre-persist que nos sirve para asignar la fecha del sistema al momento de
	// crear un cliente
	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}
	
	//Getters y Setters
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
	
	/**
	 *  Serial ID generado automáticamente
	 */
	private static final long serialVersionUID = 1L;


}
