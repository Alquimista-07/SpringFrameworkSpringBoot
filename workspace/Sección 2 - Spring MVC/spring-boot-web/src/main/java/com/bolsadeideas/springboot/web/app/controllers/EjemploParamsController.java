package com.bolsadeideas.springboot.web.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/params")
public class EjemploParamsController {
	
	@GetMapping("/string")
	/*
	 * NOTA: Para pasar parametros por la url usamos el decorador @RequestParam al cual se le pasa el
	 *       tipo el nombre del parámetro y la variable. Adicionalmete podemos también enviar un valor
	 *       por defecto usando el defaultValue = "Sin parámetro" o indicando si el campo es requerido 
	 *       con el required que es un booleano
	 */
	public String param( @RequestParam(name = "texto", required = false, defaultValue = "Sin Parámetro") String texto, Model model ) {
		model.addAttribute("resultado", "El texto enviado es: " + texto );
		return "params/ver";
		
	}
	
	/*
	 * NOTA: Ahora vamos a crear un controlador para pasar parámetros pero usando 
	 *       thymeleaf, es decir, lo mismo anterior pero con thymeleaf enviandolos
	 *       desde el html
	 */
	@GetMapping("/")
	public String index() {
		return "params/index";
	}
	
	@GetMapping("/mix-params")
	/*
	 * NOTA: Método para enviar más de un parámetro, con strings y enteros.
	 *       Adicionalmente como no especificamos el nombre del parámetro
	 *       a través del name, por lo tanto tenemos que enviarlo tal cual 
	 *       como llamamos la variable del argumento en el método
	 */
	public String param( @RequestParam String saludo, @RequestParam Integer numero,  Model model ) {
		model.addAttribute("resultado", "El saludo enviado es: '" + saludo +"' y el número es '" + numero + "'" );
		return "params/ver";
		
	}
	
	/*
	 * NOTA: El siguiente método es otra forma de recibir parámetros en el controlador
	 *       usando el HttpServletRequest
	 */
	@GetMapping("/mix-params-request")
	public String param( HttpServletRequest request, Model model ) {
		/*
		 *  NOTA: Y a través de request obtenemos los valores enviados en los parámetros
		 *        usando el métod getInitParameter al cual le indicamos por argumento
		 *        el nombre del parámetro.
		 */
		String saludo = request.getParameter("saludo");
		
		/*
		 * NOTA: Como el getParameter regresa un string tenemos que parsear para convertir a entero
		 *       el valor, adicionalmente para evitar errores en caso de que se envíe de forma errada
		 *       y se mande un texto no se de un error, para ello usamos el try catch
		 */
		Integer numero = null;
		try {			
			numero = Integer.parseInt(request.getParameter("numero"));
		}
		// Captura le excepción y le agrega un valor por defecto cuando se da un error
		catch(NumberFormatException err) {
			System.out.println("Error: " + err);
			numero = 0;
		}
		
		model.addAttribute("resultado", "El saludo enviado es: '" + saludo +"' y el número es '" + numero + "'" );
		
		return "params/ver";
	}

}
