package com.bolsadeideas.springboot.app.models.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "facturas_items")
public class ItemFactura implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Acá el colum no es necesario ya que el atributo se llama igual que en la base de datos
	private Integer cantidad;
	
	// Creamos la relación muchos items factura con un producto
	// Y por defecto va a crear el atributo producto_id en la tabla
	// item factura correspondiente a la llave foranea.
	// NOTA: Adicionalmente se nos va a presentar un problema con los itemas al carga perezosa al momento de serializar el JSON 
	//       por lo tanto para corregirlo es modificar el fetch por EAGER pero esto es poco eficiente, por lo tanto vamos a dejarlo
	//       con LAZY como estaba y la solución que es un poco más eficiente que se va a usar es agregar la anotación @JsonIgnoreProperties
	//       el cual contiene un arreglo con los atributos que queremos ignorar. Adicionalmente esta anotación puede ir acá sobre el atributo
	//       o también podemos agregar la anotación directamente sobre la clase
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "producto_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Producto producto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	// Método para calcular el importe
	public Double calcularImporte() {
		return cantidad.doubleValue() * producto.getPrecio();
	}

	private static final long serialVersionUID = 1L;

}
