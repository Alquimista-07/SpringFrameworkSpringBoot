package com.bolsadeideas.springboot.web.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

}
