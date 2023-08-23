package com.bolsadeideas.springboot.di.factura.app.models.domain;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
/*
 * NOTA: Acá hacemos el cambio para que este componente no se Singleton (Instancia única) sino que
 *       sea del tipo Request. Y por lo tanto ahora esta factura, este Bean va a durar lo que dura 
 *       una petición HTTP de usuario, por lo tanto cada usuario que se conecte va a tener una factura
 *       distinta y propia, por lo tanto si modificamos algún valor, dicho valor no se altara para el
 *       resto de usuarios.
 *       
 *       En este ejemplo cuando ejecutamos la aplicación y si refrescamos el navegador varias veces
 *       nos vamos a dar cuenta que sigue concatenando los valores y no los reemplaza ya que el cliente
 *       aún continua siendo Singleton. Por lo tanto nos indica que el objeto se esta construyendo en 
 *       cada Request, en cada petición y no una sola vez como cuando estaba en Singleton.
 *       También por cada refresh en la consola nos va a mostar lo que estamos haciendo en el destroy.
 *       
 *       OJO: Lo anteriormente mencionado con respecto a que se concatena varias veces es porque el cliente
 *            Singleton por lo tanto para verlo aplicado se debe comentar la anotación @RequestScope en
 *            el cliente.
 */

//OJO Para ver la funcionalidad, quitar este comentario y comentar los otros dos ejemplos de contexto.
@RequestScope

/*
 * NOTA: Adiconalmente también tenemos el SessionScope que es otra anotación de contexto y es para 
 *       trabajar con sesiones
 *       
 *       Por lo tanto el SessionScope lo usamos para marcar nuestro objeto como de sesión, por ejemplo
 *       si queremos trabajar con un carro de compras, con un sistema de autenticación.
 *       
 *       OJO: Un tema importante que hay que tener en cuenta en cualquier objeto, clase componente que 
 *            queramos guardar en una sesión HTTP, debe implementar la interface Serializable porque 
 *            cuando se transportar o se almacena un objeto de Java, por ejemplo si lo queremos transportar
 *            guardar en la memoria o bien en un archivo XML o JSON y también en sesiones HTTP. 
 *            
 *            Por lo tanto se necesita la inteface Serializable y porque convierte el objeto en una secuencia 
 *            de bytes para que se pueda guardar y transportar en los tipos de almacenamiento ya que después 
 *            de que esta estructura está serializada ya sea en una sesión HTTP o un JSON o en el ORM se va a
 *            restaurar en un objeto de JAVA que es idéntico en todo sentido al original, incluso el valor de
 *            sus atributos el estado interno, por lo tanto este nuevo objeto es un clone perfecto del original.
 */

// OJO Para ver la funcionalidad, quitar este comentario y comentar los otros dos ejemplos de contexto.
//@SessionScope

/* NOTA: Tembién tenemos el ApplicationScope que es otra anotación de contexto y es 
*        muy parecido al Singleton (Instancia única) pero se diferencia es que este es
*        Singleton que se guarda en el contexto Servlet (Servlet Context) y no en el
*        Application Context de Spring.
*        
*        Por ejemplo en una aplicación web con Spring vamos a tener siempre un solo Application
*        Context y ahí es Singleton, pero en una aplicación web podríamos tener más de un contexto
*        Servlet
*/
//OJO Para ver la funcionalidad, quitar este comentario y comentar los otros dos ejemplos de contexto.
// @ApplicationScope

/*
 * NOTA: Para finalizar exite otro contexto que es el Prototyper, pero en realidad no se aplica tanto para
 *       aplicaciones web, Spring MVC más bien son para aplicaciones stand alone ya sea de consola por ejemplo,
 *       la única difeferencia es que con Singleton tenemos una sola instancia en el contenedor y con prototype
 *       podemos tener más de una instancia del mismo Bean.
 */
public class Factura implements Serializable {

	/**
	 * NOTA: Al implementar la interface Serializable, nos pide que generemos el serialVersiónUID por lo tanto 
	 *       lo generamos automáticamente
	 */
	private static final long serialVersionUID = 307174459608945829L;

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
