package com.bolsadeideas.springboot.di.app.models.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Factura {
	
	// Atributos
	/*  NOTA: El titulo de la factura lo podemos guardar en un archivo properties, por lo tanto
    los inyectamos usando el decorador @Value para inyectar sus correspondientes valores.
    */
    @Value("${descripcion.factura}")
	private String descipción;
	
	/*
	 * NOTA: Inyectamos el cliente usando el @Autowired
	 */
	@Autowired
	private Cliente cliente;
	
	private List<ItemFactura> items;
	
	// Getters y Setters
	public String getDescipción() {
		return descipción;
	}
	public void setDescipción(String descipción) {
		this.descipción = descipción;
	}
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
	
	

}
