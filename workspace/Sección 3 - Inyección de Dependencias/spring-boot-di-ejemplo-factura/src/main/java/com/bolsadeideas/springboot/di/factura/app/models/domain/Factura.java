package com.bolsadeideas.springboot.di.factura.app.models.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Factura {

	// Atributos
	/*
	 * NOTA: El titulo de la factura lo podemos guardar en un archivo properties,
	 * por lo tanto los inyectamos usando el decorador @Value para inyectar sus
	 * correspondientes valores.
	 */
	@Value("${factura.descripcion}")
	private String descripcion;

	/*
	 * NOTA: Inyectamos el cliente usando el @Autowired
	 */
	@Autowired
	private Cliente cliente;

	/*
	 * NOTA: Inyectamos el listado de los items, y como tenemos un solo componente,
	 * una sola implementación, un solo método, lo podemos inyectar de forma
	 * directa, pero si tuviermos más de una podríamos hacer la inyección usando la
	 * anotación @Primary o bien con el @Qualifier y el nombre del Bean
	 */
	@Autowired
	private List<ItemFactura> items;

	// Getter
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
