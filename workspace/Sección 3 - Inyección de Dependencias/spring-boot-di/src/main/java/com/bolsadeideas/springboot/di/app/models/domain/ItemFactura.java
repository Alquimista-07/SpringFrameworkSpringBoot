package com.bolsadeideas.springboot.di.app.models.domain;

public class ItemFactura {
	
	// Atributos
	private Producto producto;
	
	private Integer cantidad;
	
	// Constructor
	public ItemFactura(Producto producto, Integer cantidad) {

		this.producto = producto;
		this.cantidad = cantidad;
	}
	
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
