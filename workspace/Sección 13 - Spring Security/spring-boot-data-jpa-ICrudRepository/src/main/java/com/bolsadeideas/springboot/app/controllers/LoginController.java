package com.bolsadeideas.springboot.app.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
	
	// El principal es bien importante ya que nos permite realizar las validaciones
	// respecto al inicio de sesión
	@GetMapping("/login")
	public String login( @RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = false) String logout, Model model, Principal principal, RedirectAttributes flash ) {
		
		System.out.println(principal);
		
		if( principal != null ) {
			// Ya había iniciado sesión por lo tanto no es necesario volver a solicitarlo
			// y simplemente rediregimos a la página de inicio
			flash.addFlashAttribute("info", "Ya ha iniciado sesión.");
			return "redirect:/";
		}
		
		if( error != null ) {
			model.addAttribute("error", "Error al iniciar sesión: Nombre de usuario o contraseña incorrectos, por favor vuelva a intentarlo.");
		}
		
		if ( logout != null ) {
			model.addAttribute("success", "Ha cerrado sesión con éxito!");
		}
		
		// No había iniciado sesión	
		return "login";
	}

}
