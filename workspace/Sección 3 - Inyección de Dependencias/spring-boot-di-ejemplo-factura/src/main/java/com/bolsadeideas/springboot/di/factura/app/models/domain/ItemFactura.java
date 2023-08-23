package com.bolsadeideas.springboot.di.factura.app.models.domain;

public class ItemFactura {

	// Atributos
	private Producto producto;

	private Integer cantidad;

	// Getters y Setters
	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

}
