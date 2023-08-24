package com.bolsadeideas.springboot.form.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FormController {
	
	/*
	 * NOTA: Método para mostrar el formulario en la pantalla
	 */
	@GetMapping("/form")
	public String form(Model model) {
		model.addAttribute("titulo", "Formulario Usuario");
		return "form";
	}
	
	/*
	 * NOTA: Método para enviar y procesar los datos
	 */
	@PostMapping("/form")
	/*
	 * NOTA: Para capturar los valores del formulario usamos la anotación
	 *       @RequestParam y se tiene que indicar con el mismo nombre como
	 *       se indico en el atributo name en el formulario html.
	 *       
	 *       Recordemos que también podemos usar el name o el value del 
	 *       @RequestParam para indicar el nombre del input, por ejemplo
	 *       
	 *       @RequestParam(name="email") String correo
	 */
	public String procesarFormulario(Model model, @RequestParam(name = "username") String username,
								                  @RequestParam String pwd,
								                  @RequestParam String email) {
		// Pasamos los datos a la vista con model
		model.addAttribute("titulo", "Resultador Form");
		
		model.addAttribute("username", username);
		model.addAttribute("pwd", pwd);
		model.addAttribute("email", email);
		
		return "resultado";
	}
	

}
