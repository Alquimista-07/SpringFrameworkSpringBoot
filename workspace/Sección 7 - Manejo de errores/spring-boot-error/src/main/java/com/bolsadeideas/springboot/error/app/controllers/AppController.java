package com.bolsadeideas.springboot.error.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bolsadeideas.springboot.error.app.errors.UsuarioNoEncontradoException;
import com.bolsadeideas.springboot.error.app.models.domain.Usuario;
import com.bolsadeideas.springboot.error.app.services.UsuarioService;

@Controller
public class AppController {
	
	@Autowired
	private UsuarioService usuarioService;
	
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
	
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable Integer id, Model model) {
		Usuario usuario = usuarioService.obtenerPorId(id);
		
		if ( usuario == null ) {
			throw new UsuarioNoEncontradoException(id.toString());
		}
		
		model.addAttribute("usuario", usuario);
		model.addAttribute("titulo", "Detalle usuario: ".concat(usuario.getNombre()));
		return "ver";
	}

}
