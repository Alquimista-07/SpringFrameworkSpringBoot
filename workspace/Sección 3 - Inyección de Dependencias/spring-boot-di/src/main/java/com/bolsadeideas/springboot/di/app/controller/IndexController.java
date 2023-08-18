package com.bolsadeideas.springboot.di.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bolsadeideas.springboot.di.app.models.service.MiServicio;

@Controller
public class IndexController {
	
	/*
	 * NOTA: Como estamos trabajando sin inyección de dependencias vamos a implentar el 
	 *       servicio con el operador new, por lo tanto va a quera acoplado a esta 
	 *       implementación concreta.
	 *       
	 *       Por lo tanto en un aplicación grande o mediana, vamos a tener un problema 
	 *       de mantenimiento y escalabilidad ya que al esta acoplador a una implemen-
	 *       tación concreta cuando algo cambie dentro de un método va a causar que se
	 *       tenga que cambiar en diferentes lugares por lo tanto la implementación 
	 *       podría cambiar e incluso el nombre de la calse también podría cambiar y
	 *       se podrían tener muchos controladore que este usando dicho servicio por lo
	 *       tanto se tendría que cambiar en todos los lugares.
	 *       
	 *       Entonces para evitar ese problema primero es usar inyección de dependencias
	 *       y segundo interfaces. Por lo tanto con la inyección de dependencias es al 
	 *       revés ya que con la inyección de dependencias la idea es que nosotros no
	 *       llamemos al objeto, no lo instanciemos sino que el objeto en este caso el 
	 *       contenedor de spring a través de los beans (componentes) spring nos llama al
	 *       controlador y nos inyecta la instancia, es decir, la provee y de ahí viene el
	 *       principio de inyección de dependencias llamado Hollywood el cual basicamente
	 *       dice "No nos llames nosotros te llamamos"
	 */
	private MiServicio servicio = new MiServicio();
	
	@GetMapping({ "/", "/index", "" })
	public String index(Model model) {
		model.addAttribute("objeto", servicio.operacion());
		return "index";
	}

}
