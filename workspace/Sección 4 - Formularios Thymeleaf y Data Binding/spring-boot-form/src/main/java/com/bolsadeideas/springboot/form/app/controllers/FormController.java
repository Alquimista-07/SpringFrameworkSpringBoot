package com.bolsadeideas.springboot.form.app.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bolsadeideas.springboot.form.app.models.domain.Usuario;

import jakarta.validation.Valid;

@Controller
public class FormController {
	
	/*
	 * NOTA: Método para mostrar el formulario en la pantalla
	 */
	@GetMapping("/form")
	public String form(Model model) {
		model.addAttribute("titulo", "Formulario Usuario");
		// En la primera creación del formulario para que no de error debemos crear un objeto usuario vacío
		// y pasarlo al formulario si no nos da un error NullPointerException.
		Usuario usuario = new Usuario();
		model.addAttribute("usuario", usuario);
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
	/*
	 * NOTA: Para las validaciones es necesario indicar la anotación @Valid y en el POJO 
	 *       se indica la regla de validación.
	 */
	/*
	 * NOTA IMPORTATE: Si queremos cambiar el nombre del paramétro del POJO en este caso la clase usuario
	 *                 y queremos llamarla de otra forma por ejemplo user, hacemos uso de la anotación 
	 *                 @ModelAttribut la cual se usa de la sigueiente forma:
	 *                 
	 *                 public String procesarFormulario(Model model, @Valid @ModelAttribute("user") Usuario usuario, BindingResult result) {
	 *                 
	 *                 Y ya del lado de la vista ya no usamos usuario sino user
	 */
	public String procesarFormulario(Model model, @Valid Usuario usuario, BindingResult result) {
		
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
		
		/*
		 *  NOTA: Antes de pasar el formulario tenemos que validar si el formulario es válido
		 *        y para ello nos apoyamos en la interface BindingReult que es un argumento que 
		 *        colocamos en el método. Y esta interface es propia de spring y representa el
		 *        resultado de la validación, es decir, contiene los mensajes de error en caso 
		 *        de que ocurran errores. Y este se inyecta automáticamente cuando el objeto esta
		 *        anotado con @Valido pero este tiene una restricció muy importante que se tiene
		 *        que respetar y es que siempre el BindingResult tiene que estar justo después
		 *        del objeto que se va a validar
		 */
		if( result.hasErrors() ) {
			
			Map<String, String> errores = new HashMap<>();
			// Usamos el result para obtener el mensaje de error y vamos poblando el mapa
			// con los mensajes de error.
			// Recorremos por cada mensaje de error.
			result.getFieldErrors().forEach(err -> {
				// Y acá asignamos la llave (nombre del campo obtenido con getField) y el valor
				// es el mensaje de error que queremos manejar.
				errores.put(err.getField(), "El campo ".concat(err.getField().concat(" ").concat(err.getDefaultMessage())));
			});
			
			// Pasamos a la vista los errores
			model.addAttribute("error", errores);
			
			return "form";
			
		}
		
		// NOTA: Se realizo el refactor para pasar directamente el objeto
		model.addAttribute("usuario", usuario);
		
		return "resultado";
	}
	

}
