package com.bolsadeideas.springboot.web.app.controllers;

/*
 *  Importaciones de paquetes
 */
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*
 *  NOTA: Esta clase la creamos como una clase java normal y a la cual posteriormente le agregamos la
          anotación o decorador @Controller y lo importamos. Adicionalmente al agregar este decorador 
          automaticamente va a indicarla a nuesta clase main de spring la cual usa el decorador 
          @SpringBootApplication que a su vez internamente usa el decorador @ComponentScan que escanee 
          y tome las clases que usan el deocrador @Controller.
 */
@Controller
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
	public String index() {
		/*
		 *  NOTA: Acá se retona el nombre de la vista, por lo tanto debe existir una plantilla llamada con el mismo nombre,
		          en este caso del ejemplo sería index.html y dicha plantilla debería estar en la ruta de los templates que
		          sería en src/main/resouces/templates
		 */
		return "index";
	}
	
}
