package com.bolsadeideas.springboot.di.factura.app;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bolsadeideas.springboot.di.factura.app.models.domain.ItemFactura;
import com.bolsadeideas.springboot.di.factura.app.models.domain.Producto;

@Configuration
public class AppConfig {
	
	/*
	 * NOTA: Método para que retorne un list de los items 
	 *       de la factura.
	 *       Adicionalmente le colocamos un nombre al Bean 
	 *       en caso de que tengamos más de in Bean del tipo 
	 *       List<ItemFactura>
	 */
	@Bean("itemsFactura")
	public List<ItemFactura> registrarItems(){
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
