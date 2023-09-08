package com.bolsadeideas.springboot.horariointerceptor.app.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
	
	// Creamos atributos que permiten traer la información definida en el application properties
	@Value("${config.horario.apertura}")
	private Integer apertura;
		
	@Value("${config.horario.cierre}")
	private Integer cierre;
	
	@GetMapping({"/", "/index"})
	public String index(Model model) {
		model.addAttribute("titulo", "Bienvenido al horario de atención a clientes");
		return "index";
	}
	
	@GetMapping("/cerrado")
	public String cerrado(Model model) {
		
		// El StringBuilder es un objeto de java que permite crear strings que sean mutables,
		// es decir que podemos irle cambiando la instancia, ir concatenando, agregando sin
		// tener que crear nuevos objetos.
		StringBuilder mensaje = new StringBuilder("Cerrado, por favor visítenos desde las ");
		mensaje.append(apertura);
		mensaje.append(" y las ");
		mensaje.append(cierre);
		mensaje.append(" hrs. Gracias.");
		
		model.addAttribute("titulo", "Fuera del horario de atención");
		model.addAttribute("mensaje", mensaje );
		return "cerrado";
	}

}
