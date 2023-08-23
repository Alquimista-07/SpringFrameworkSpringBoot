package com.bolsadeideas.springboot.di.app;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.bolsadeideas.springboot.di.app.models.domain.ItemFactura;
import com.bolsadeideas.springboot.di.app.models.domain.Producto;
import com.bolsadeideas.springboot.di.app.models.service.IServicio2;
import com.bolsadeideas.springboot.di.app.models.service.MiServicio2;
import com.bolsadeideas.springboot.di.app.models.service.MiServicioComplejo;

/*
 * NOTA: Existe otra forma de registrar un componente dentro del contenedor de Spring y es una alternativa a usar el decorador @Component
 *       o la anotación @Service. Por lo tanto en vez de usar lo anteriormente mencionado, se va a usar una clase de configuración de Spring
 *       anotada con @Configuration para que sirva como una definición para crear componentes o Beans de Spring, mediante métodos y la 
 *       anotación @Bean.
 *       
 *       Para continuar con lo anterior mente mencionado, vamos a crear la clase de configuración, antoandola con @Configuration y esta va a
 *       tener métodos, los cuales crean los componentes y retornan la instancia, el objeto.
 *       
 *       También hay que realtar que esta clase configuration debe estar dentro del paquete principal del proyecto (com.bolsadeideas.springboot.di.app)
 */
@Configuration
public class AppConfig {
	
	/*
	 * NOTA: Adicionalmente también le podemos asingar el nombre dentro de paréntesis, para poderlo usar tal cual como se hace cuando trabajanos
	 *       con los @Qualifiers (calificadores).
	 *       
	 *       Adicionalmente si no especificamos el @Bean y ejecutamos el proceso sin
	 *       tener registrado ya sea con el @Bean o el @Component, esto nos va a dar
	 *       error debido a que no puede inyectar nada.
	 *       
	 *       Y adicionalmente también podemos usar el @Qualifier en el controlador para llamar
	 *       el servicio.
	 */
	@Bean("miServicioSimple")
	public IServicio2 registrarMiServicio() {
		return new MiServicio2();
	}
	
	/*
	 * NOTA: Acá si no especificamos cual es el primario nos va a dar error ya que encuentra
	 *       dos candidatos para inyectar, por lo tanto para indicar cual es el primario 
	 *       indicamos con luego de la anotación @Bean la anotación @Primary para que inyecte
	 *       este.
	 *       Y adicionalmente también podemos usar el @Qualifier en el controlador para llamar
	 *       el servicio.
	 */
	@Bean("miServicioComplejo")
	@Primary
	public IServicio2 registrarMiServicioComplejo() {
		return new MiServicioComplejo();
	}
	
	/*
	 * NOTA: Método para que retorne un list de los items 
	 *       de la factura.
	 *       Adicionalmente le colocamos un nombre al Bean 
	 *       en caso de que tengamos más de in Bean del tipo 
	 *       List<ItemFactura>
	 */
	@Bean("itemsFactura")
	public List<ItemFactura> registrarItemsFactura(){
		/*
		 * NOTA: Creamos unos objetos de producto, de itemas factura, asignamos
		 *       los productos y los guardamos en un arraylist
		 */
		Producto prod1 = new Producto("Cámara Sony", 100);
		Producto prod2 = new Producto("Bicicleta Bianchi aro 26", 200);
		
		ItemFactura linea1 = new ItemFactura(prod1, 2);
		ItemFactura linea2 = new ItemFactura(prod2, 4);
		
		/*
		 * NOTA: Usando Arrays praticamente sería lo mismo que si crearamos la instancia de
		 * ArrayList = new ArrayList y agregaramos los elementos usando el .add()
		 */      
		return Arrays.asList(linea1, linea2);
	}

}
