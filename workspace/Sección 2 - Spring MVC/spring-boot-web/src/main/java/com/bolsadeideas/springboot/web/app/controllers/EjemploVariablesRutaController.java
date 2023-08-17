package com.bolsadeideas.springboot.web.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/variables")
public class EjemploVariablesRutaController {
	
	/*
	 * NOTA: Mapeamos y creamos una ruta index para mostrar el ejemplo de @PathVarible
	 *       con varios parámetros
	 */
	@GetMapping("/")
	public String index( Model model ) {
		model.addAttribute("titulo", "Recibir parámetros de la ruta (@PathVariable)");
		return "variables/index";
	}

	/*
	 * NOTA: Ahora vamos a realizar otra forma de pasar parámetros
	 *       mediante la ruta URL al controlador, pero esta vez en
	 *       la ruta que se esta mapeando los métodos handler, esta
	 *       forma es propia de los métodos de spring.
	 *       
	 *       Por lo tanto cuando creamos la ruta indicamos que esta 
	 *       contiene una variable la cual la indicamos dentro de 
	 *       llaves con su respectivo nombre. Con la diferencia de
	 *       que en los argumentos del método no usamos el @RequestParam
	 *       sino el @PathVariable y el cual en el fondo es una ruta que
	 *       contiene un tramo variable que en este caso sería la variable
	 *       texto.
	 *       
	 *       Adicionalmente se de debe indicar el tipo primitivo y adicionalemnte
	 *       el nombre debe corresponder con el del path.
	 *       Otra cosa que hay que mencionar es que en caso de que el nombre sea
	 *       diferente podemos indicar el name similar a como se hace con el 
	 *       @RequestParam
	 */
	@GetMapping("/string/{texto}")
	public String variables( @PathVariable(name = "texto") String textoOtro, Model model ) {
		model.addAttribute("titulo", "Recibir parámetros de la ruta (@PathVariable)");
		model.addAttribute("resultado", "El texto enviado en la ruta es: " + textoOtro );
		return "variables/ver";
	}
	
	/*
	 * NOTA: Pasar más de un parámetro en el @PathVariable, para ello lo que se hace es
	 *       ir seaparandolos con un /
	 */
	@GetMapping("/string/{texto}/{numero}")
	public String variables( @PathVariable String texto, @PathVariable Integer numero, Model model ) {
		model.addAttribute("titulo", "Recibir parámetros de la ruta (@PathVariable)");
		model.addAttribute("resultado", "El texto enviado en la ruta es: " + texto
				+ " y el número enviado en el path es: " + numero);
		return "variables/ver";
	}
	
}
