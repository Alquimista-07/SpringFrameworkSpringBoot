package com.bolsadeideas.springboot.web.app.controllers;

import java.util.ArrayList;
import java.util.List;

/*
 *  Importaciones de paquetes
 */
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bolsadeideas.springboot.web.app.models.Usuario;

/*
 *  NOTA: Esta clase la creamos como una clase java normal y a la cual posteriormente le agregamos la
          anotación o decorador @Controller y lo importamos. Adicionalmente al agregar este decorador 
          automaticamente va a indicarla a nuesta clase main de spring la cual usa el decorador 
          @SpringBootApplication que a su vez internamente usa el decorador @ComponentScan que escanee 
          y tome las clases que usan el deocrador @Controller.
 */
@Controller
/*
 * NOTA: Podemos mapear el controlador con @RequestMapping una ruta base que va a ser en comun para todos
 *       los métodos del controlador, es decir, todos los méotodos handler, y este decorador recibe una ruta
 *       url que sea genérica base para todos los métodos, y esta ruta sería de primer nivel, y la de los 
 *       métodos de segundo nivel, por lo tanto todas las rutas de los métodos deben pasar primero por la 
 *       principal o de primer nivel por ejemplo: http://localhost:8080/app/index
 */
@RequestMapping( "/app" )
public class IndexController {

	/*
	 *  NOTA: Ahora hay que tener en cuenta que un controlador va a tener métodos de acción o handler
	          que va a manejar una petición http del usuario, por ejemplo para mostrar un formulario,
	          cargar datos, manejar un listado de algo, consultas a la base de datos (guardar, insertar,
	          eliminar, actualizar), es decir, cada método handler representa una página web que hace
	          algo y que trabaja con los datos de la aplicación, y dichos datos se van a representar o 
	          mostrar en una vista dentro de la respuesta, que para el curso es usando thymeleaf.
	          
	          Entonces básicamente el controlador se encarga de manejar las peticiones del usuario, mostar
	          la página, la respuesta con los datos que el usuario ha solicitado.
	 */
	         
	
	/*
	 *  NOTA: Los métodos en el cotrolador siempre son públicos, adicionalmente el controlador puede tener varios
	          métodos y cada método va a manejar una página, una petición http (GET, POST, DELETE, PUT, ..., etc.) 
	          distinta.
	 */
	
	/*
	 *  NOTA: Tenemos que mapear que básicamente es relacionar el método (para este caso index) a una ruta url para
	          que el cliente, el usuario cuando navegue en la aplicación por ejemplo en la url indique es el index
	          automáticamente se invoque este método, cargue y muestre la vista index.html
	
	          Entonces para mapear usamos el decorador @RequestMapping que recibe un parametro value, y otro correspondiente
	          al método http adicionalmente también tenemos que importar dicho decorador.
	
	          El siguiente es un ejemplo de como se usaria: @RequestMapping(value = "/index", method = RequestMethod.GET)
	
	          Adicionalmente también hay que tener en cuenta que también se tienen alias de @RequestMapping el cual es por 
	          ejmplo el @GetMapping(value = "/index"), el @PostMapping(value = "/index") y pues hay que tener en cuenta
	          que por ejemplo para el @GetMapping() el value es el parámetro por defecto por lo tanto podríamosomitir la 
	          asignación e indicarlo de la siguiente forma: @GetMapping("/index")
	
	          También hay que tener en cuenta que estos alias  los tenemos que importar y existe uno por cada método http 
	          y que básicamente son lo mismo que el @RequestMapping() solo que más abreviados y se puede usar el que 
	          queramos.
	 */
	
	/*
	 *  NOTA: Un méoto puede estar mapeado a más de una ruta solo que se indica dentro de llaves y se separan por coma, 
              por ejemplo:
	       
	          @GetMapping( { "index.html", "/", "home" } )
	
	 */
	@GetMapping( { "/index", "/", "/home" } )
	public String index( Model model ) {
		/*
		 * NOTA: Para pasar datos a la vista tenemos una primera forma que es usando  la interface model a la cual le 
		 *       especificamos la llave, y el valor para posteriormente del lado del html especificar el xmlns (Namespace) 
		 *       y asignar la llave tal cual como la especificamos en el model.
		 *       
		 *       También existe una segunda forma para pasar la información que es el ModelMap, con la diferencia de
		 *       que esta es una clase pero de la misma forma maneja el addAttribute con su clave y valor, es decir, 
		 *       que no hay ninguna diferencia con el Model.
		 *       
		 *       Una tercera forma es usar directamente la interface de Java Map para pasar valores, indicando los tipos, 
		 *       un ejemplo sería el siguiente:
		 *       
		 *        	public String index( Map<Sring, Object> map ){
		 *        
		 *        		map.put( "titulo", "Hola Spring Framework" )
		 *          	return index;
		 *          
		 *        	}
		 *        
		 *        Tenemos una cuarta forma que es el el ModelAndView que es otra clase y se usa de la siguiente forma:
		 *        
		 *        	public ModelAndView index( ModelAndView mv ){
		 *        
		 *        		mv.addObject( "titulo", "Hola Spring Framework!!!..." );
		 *          
		 *          	// Indicamos el nombre de la vista con el setViewName
		 *        		mv.setViewName("index");
		 *        	
		 *          	return mv;
		 *          
		 *        	}
		 *        
		 */
		model.addAttribute("titulo", "Hola Spring Framework!!!...");
		/*
		 *  NOTA: Acá se retona el nombre de la vista, por lo tanto debe existir una plantilla llamada con el mismo nombre,
		          en este caso del ejemplo sería index.html y dicha plantilla debería estar en la ruta de los templates que
		          sería en src/main/resouces/templates
		 */
		return "index";
	}
	
	/*
	 * NOTA: En el @RequestMapping si no indicamos el method por defecto el que se asigna es el GET.
	 */
	@RequestMapping( "/perfil" )
	public String perfil( Model model ) {
		
		// Creamos la instancia de la clase.
		Usuario usuario = new Usuario();
		
		// Pasamos los valores
		usuario.setNombre("Pepito");
		usuario.setApellido("Pérez");
		usuario.setEmail("test@test.com");
		
		model.addAttribute( "usuario", usuario );
		model.addAttribute("titulo", "Perfil de Usuario: ".concat(usuario.getNombre()));
		
		return "perfil";
	}
	
	@RequestMapping( "/listar" )
	public String listar( Model model ) {
		
		// Creamos el arreglo
		List<Usuario> usuarios = new ArrayList<>();
		
		model.addAttribute("usuarios", usuarios);
		
		model.addAttribute("titulo", "Lista de Usuarios: ");
		
		return "listar";
	}
	
}
