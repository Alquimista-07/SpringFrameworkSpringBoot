package com.bolsadeideas.springboot.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "facturas")
public class Factura implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String descripcion;
	
	private String observacion;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "create_at")
	private Date createAt;

	// NOTA: El atributo cliente es fundamental para la relación ya que acá debemos
	//       crear las relaciones como están en base de datos y se hacen a nivel de objeto.
	//       Ahora para mapear la relación de muchas facturas un solo cliente usamos la anotación
	//       que la representa @ManyToOne
	@ManyToOne
	private Cliente cliente;
	
	// Creamos un evento pre-presist para generar la fecha del día de creación
	// automáticamente y no tener que ingresarla por el formulario en la vista.
	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}
	
	// Creamos la relación una factura con muchos items factura
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	// Acá como la relación es en un solo sentido debemos indicar cual es la llave foranea
	// que va a relacionar la factura con items factura y la cual va a ser creada en la tabla
	// items factura. Por lo tanto acá no es necesario indicar el mappedBy = "cliente" como 
	// paso con cliente y factura que tienen una relación bidireccional.
	@JoinColumn(name = "factura_id")
	private List<ItemFactura> items;

	public Factura() {
		this.items = new ArrayList<ItemFactura>();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	// NOTA: Como al generar el XML se genera un loop debido a que cliente y factura tienen una relación bidireccional
	//       es necesario bloquear el acceso de parte de la factura ya que por la parte del cliente no podemos hacerlo 
	//       para esto entonces anotamos con @XmlTransient la cual indica que cuando se serializa no va a llamar este
	//       método get
	@XmlTransient
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public List<ItemFactura> getItems() {
		return items;
	}

	public void setItems(List<ItemFactura> items) {
		this.items = items;
	}
	
	// Método para agregar item por item a la factura
	public void addItemFactura( ItemFactura item ) {
		this.items.add(item);
	}
	
	// Metodo para calcular el gran total de la factura.
	public Double getTotal() {
		Double total = 0.0;
		
		int size = items.size();
		
		for( int i = 0; i < size; i++ ) {
			// Por cada elemento calculamos el importe y lo sumamos para totalizar
			total += items.get(i).calcularImporte();
		}
		
		// Retornamos la sumatoria completa de la factura
		return total;
	}

	// Atributo de la interace Serializable
	private static final long serialVersionUID = 1L;
	

}
