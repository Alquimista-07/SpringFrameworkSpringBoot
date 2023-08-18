package com.bolsadeideas.springboot.di.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bolsadeideas.springboot.di.app.models.service.MiServicio;
import com.bolsadeideas.springboot.di.app.models.service.MiServicio2;

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
	
	/*  
	 *       -----------------------------------------------------------------------------------
	 *       EL SIGUIENTE EJEMPLO REPRESENTADO ES CUANDO NO USAMOS INYECCIÓN DE DEPENDENCIAS Y 
	 *       LO DECLARABAMOS DE LA SIGUIENTE FORMA: 
	 *       
	 *       OJO: Para ver la explicación y funcionamiento del ejemplo ir al commit 34. Añadiendo 
	 *       la clase de servicio (sin inyección de dependecias):
	 *       
	 *				private MiServicio servicio = new MiServicio();
	 *       
	 *				@GetMapping({ "/", "/index", "" })
	 *				public String index(Model model) {
	 *					model.addAttribute("objeto", servicio.operacion());
	 *					return "index";
	 * 				}
	 *       -----------------------------------------------------------------------------------
	 */
	
	/*
	 * NOTA: Ahora como se va a usar inyección de dependecias ya no usamos el new como se muestra
	 *       en el ejemplo anterior, es decir, que se va y desacoplamos el componente y lo vamos a
	 *       inyectar usanso la anotación @Autowired y la cual no significa nada más, solo sirve para 
	 *       inyectar un objeto que estar registrado en el contenedor de Spring.
	 *       
	 *       -------------------------------
	 *       OJO NOTA IMPORTANTE EJERCICIO: 
	 *       -------------------------------
	 *       En este ejemplo aún tenemos que modificar el tipo de dato acá ya que aún esto sigue estando 
	 *       acoplado a este nombre de clase concreta,  entonces la idea es usar tipos genéricos por 
	 *       ejemplomclases abstractas e interfaces y por lo general son interfaces, es decir, en vez de 
	 *       inyectar por el tipo cocreto se inyecta por la interface y así se desacopla completamente
	 *       cualquier implementación concreta y de hecho así no se tendrían que estar modificando 
	 *       los controladores que estén usando ese componente cuando se quiera cambiar una implementación
	 *       a otra ya que es genérico, es una interface y lo podría implementar más de una clase concreta
	 */
	
	@Autowired
	private MiServicio2 servicio;
	
	@GetMapping({ "/", "/index", "" })
	public String index(Model model) {
		model.addAttribute("objeto", servicio.operacion());
		return "index";
	}

}
