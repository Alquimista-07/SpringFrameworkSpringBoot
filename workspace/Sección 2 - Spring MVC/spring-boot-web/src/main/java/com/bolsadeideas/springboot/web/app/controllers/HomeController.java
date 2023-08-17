package com.bolsadeideas.springboot.web.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
/*
 * NOTA: El @RequestMapping lo omitimos ya que la idea es que este archivo
 *       contenga una única ruta 
 */
public class HomeController {
	
	/*
	 * NOTA: Este método redirecciona a otro lugar para ello usamos 
	 *       la palabra reservada redirect y la redirección la indicamos
	 *       luego de los dos puntos
	 */
	@GetMapping("/")
	public String home() {
		/*
		 * NOTA: Redirigimos la index, para ello pasamos la ruta a redireccionar.
		 *       Ahora cuando usamos el redirect: se reinicia el request y la ruta
		 *       en el navegador cambia. 
		 *       
		 *       Una segunda forma sin reinicar el reques es usando el forward el 
		 *       cual se usa de la siguinte forma:
		 *       
		 *       return "forward: /app/index";
		 *       
		 *       Y al usar esta forma nos damos cuenta que la ruta en la url del
		 *       navegador no cambia. 
		 *       
		 *       Por lo tanto usamos forward cuando sea para rutas propias de la
		 *       aplicación y de los controladores. Y el redirect cuando sean 
		 *       enlaces externos como por ejemplo redireccionar a https://google.com
		 */
		return "redirect:/app/index";
	}

}
