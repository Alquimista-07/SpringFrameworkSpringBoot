package com.bolsadeideas.springboot.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LocaleController {
	
	// Metodo que se encarga de manejar una petición
	@GetMapping("/locale")
	public String locale(HttpServletRequest request) {
		
		// Obtenemos la referencia a la última url
		String ultimaUrl = request.getHeader("referer");
		
		return "redirect:".concat(ultimaUrl);
	}

}
