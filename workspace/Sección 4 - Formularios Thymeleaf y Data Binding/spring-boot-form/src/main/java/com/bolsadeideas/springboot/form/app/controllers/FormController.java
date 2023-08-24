package com.bolsadeideas.springboot.form.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bolsadeideas.springboot.form.app.models.domain.Usuario;

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
	/*
	 *       NOTA ACTUALIZACIÓN Y MEJORA:
	 *       Mejoramos y eliminamos el @RequestParam y la instancia de la clase ya que
	 *       el formulario de forma automática se mapea a una clase POJO y en este caso
	 *       ya creamos nuestra clase POJO usuario, por lo tanto la pasamos como parametro
	 *       la clase pero si tenemos que tener en cuenta que los nombres de los atirubutos
	 *       de la clase POJO coincidan con el nombre de los atributos name del formulario.
	 *       
	 *       IMPORTANTE: Tenemos que tener los Getters y Setter en el POJO.
	 *       
	 *       public String procesarFormulario(Model model, @RequestParam(name = "username") String username,
	 *							                  @RequestParam String pwd,
	 *							                  @RequestParam String email) {
	 */
	public String procesarFormulario(Model model, Usuario usuario) {
		
		/*
		 * NOTA: Creamos la instancia de la clase, adicionalmente esta se podría inyectar
		 *       pero no tiene mucho sentido inyectar un objeto Entity un objeto POJO ya 
		 *       que representa los datos de la aplicación, por ejemplo que se guardan en
		 *       la base de datos, son datos que están guardados en algún lado y por lo 
		 *       tanto no es una clase de servicio que presta alguna lógica de negocio con
		 *       estos datos, o una clase de configuración o una clase helper o de utilidad
		 *       con funciones.
		 */       
		 
		/* NOTA ACTUALIZACIÓN:
		 *       Como ser realizo el refactor usando el POJO la instancia ya no es necesario crearla acá
		 *       ni seter los argumentos.
		 */    
		/*
		   Usuario usuario = new Usuario();
		 
		   usuario.setUsername(username);
           usuario.setPwd(pwd);
		   usuario.setEmail(email);
		 */
		
		// Pasamos los datos a la vista con model
		model.addAttribute("titulo", "Resultador Form");
		
		// NOTA: Se realizo el refactor para pasar directamente el objeto
		model.addAttribute("usuario", usuario);
		
		return "resultado";
	}
	

}
