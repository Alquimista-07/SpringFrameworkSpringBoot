package com.bolsadeideas.springboot.error.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
	
	@GetMapping("/index")
	// Colocamos una variable llamada valor que no se usa pero que nos va a servir para 
	// forzar un error, y anotamos con @SuppressWarnings() para omitir el warning que nos 
	// indica que la variable no se esta usando.
	@SuppressWarnings("unused")
	public String index () {
		//Integer valor = 100 / 0;
		
		// Obligamos un error de number format exception
		Integer valor = Integer.parseInt("10e10");
		return "index";
	}

}
