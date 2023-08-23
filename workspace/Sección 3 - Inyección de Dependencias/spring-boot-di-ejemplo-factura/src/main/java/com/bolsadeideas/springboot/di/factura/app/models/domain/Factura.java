package com.bolsadeideas.springboot.di.factura.app.models.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

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
	
	/*
	 * NOTA: Método para que inicialice el componente justo después de que el contenedor
	 *       de Spring cree e instancie el objeto y podemos hacer o ejecutar alguna tarea.
	 *       
	 *       Este método se ejecuta justo después de crear los atributos e inyectar las 
	 *       dependencias.
	 *       Adicionalmente cabe decir que con esta anotación @PostConstruct podemos modificar
	 *       datos del componente o hacer alguna tarea o un proceso justo después de que se 
	 *       construya el objeto en el contenedor de Spring, como inicializar recursos, objetos
	 *       y al final es muy parecido a un constructor pero de una forma mucho más elegando y 
	 *       y permite que Spring maneje la construcción del objeto y después nosostros 
	 *       inicializamos lo que queramos.
	 */
	@PostConstruct
	public void inicializar() {
		cliente.setNombre(cliente.getNombre().concat(" ").concat("José"));
		descripcion = descripcion.concat(" del cliente: ").concat(cliente.getNombre());
	}
	
	/*
	 * NOTA: Método para que se ejecute otra tarea justo antes de que se destruya el 
	 *       componente, es decir también podemos realizar alguna tarea.
	 *       
	 *       Y por lo tanto esto lo vamos a ver cuando se destruya el componente, que 
	 *       en este caso es cuando bajamos la aplicación y por lo tanto acá podemos 
	 *       hacer algo o ejecutar alguan tarea justo antes de que se destruya el 
	 *       componente, por ejemplo si tenemos componentes con conexiones a recursos,
	 *       podremos cerrar la conexión a estos recursos.
	 */
	@PreDestroy
	public void destruir() {
		System.out.println("Factura destruida: ".concat(descripcion));
	}

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
