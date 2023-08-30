package com.bolsadeideas.springboot.form.app.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;

//import java.util.HashMap;
//import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bolsadeideas.springboot.form.app.editors.NombreMayusculaEditor;
import com.bolsadeideas.springboot.form.app.editors.PaisPropertyEditor;
import com.bolsadeideas.springboot.form.app.editors.RolesEditor;
import com.bolsadeideas.springboot.form.app.models.domain.Pais;
import com.bolsadeideas.springboot.form.app.models.domain.Role;
import com.bolsadeideas.springboot.form.app.models.domain.Usuario;
import com.bolsadeideas.springboot.form.app.services.PaisService;
import com.bolsadeideas.springboot.form.app.services.RoleService;
import com.bolsadeideas.springboot.form.app.validation.UsuarioValidador;

import jakarta.validation.Valid;

@Controller
/*
 * NOTA: Para matener los datos del usuario, en este caso el identificador el cual es un dato que no 
 *       esta mapeado en el formulario pero que tiene que ser enviado ya que tiene un valor asignado
 *       y para que no se envíe en null a la vista resultado usamos la anotación @SessionAttributes
 *       para mantenerlo. A dicha anotación tenemos que darle un nombre, pero tiene que ser el mismo
 *       nombre con el que se pasa a la vista que en este caso al objeto lo llamamos usuario. Y dicho
 *       objeto se va a guardar en una sesión HTTP, y va a contener todos los datos que tenga independiente
 *       si están en el formulario como campo se van a mantener de forma persistente entre el formulario y
 *       cuando se envíen los datos. Pero si cambiamos algún dato ene el formulario se va a actualizar
 */
@SessionAttributes("usuario")
public class FormController {
	
	// Inyectamos la calse validadora que se creo
	@Autowired
	private UsuarioValidador validador;
	
	@Autowired
	private PaisService paisService;
	
	// Inyectamos el servicio para manejar los roles como objetos
	@Autowired
	private RoleService roleService;
	
	// NOTA: Inyectamos el property editor
	@Autowired
	private PaisPropertyEditor paisEditor;
	
	// Inyectamos el property editor de los roles
	@Autowired
	private RolesEditor roleEditor;
	
	/*
	 *  NOTA: Registramos en el initbinder, es decir, cuando se inicializa el proceso de validación
	 *        el proceso de pasar los datos, al objeto usuario.
	 */
	@InitBinder
	public void initBinder( WebDataBinder binder ) {
		// NOTA: Acá si usamos el método setValidator se nos pierden las demás validaciones
		//       que tenemos por anotación en la clase Usuario ya que reemplaza el validador
		//       por lo tanto para que esto no sucede tenemos que usar el addValidators
		binder.addValidators(validador);
		
		// Creamos el formato fecha
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// NOTA: Indicamos que sea estricto y no tolerante a ajustar el formato para evitar que 
		//       se coloquen fechas con formato incorreco para ello esto lo hacemos con el uso 
		//       del setLenient en false
		dateFormat.setLenient(false);
		
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
		
		/*
		 * NOTA: Como primer atributo recibe el tipo, como segundo atributo el nombre de campo ya que si no lo especificamos
		 *       va a aplicar para todo lo que sea String y por lo tanto todo lo va a volver a mayúscula, pero en este caso 
		 *       queremos que solo sea el nombre entonces por eso lo especificamos y como tercer atributo recibe la lase custom
		 *       que creamos y que hereda PropertyEditorSupport.
		 */
		binder.registerCustomEditor( String.class, "nombre",new NombreMayusculaEditor() );
		binder.registerCustomEditor( String.class, "apellido",new NombreMayusculaEditor() );
		
		// Registramos el property editor
		binder.registerCustomEditor(Pais.class, "pais", paisEditor);
		
		// Registramos el property editor de los roles
		// Y como primer parametro enviamos el tipo definido en la clase Usuario, como segundo el nombre del atributo en la clase Usuario y como tercer valor el editor
		binder.registerCustomEditor(Role.class, "roles", roleEditor);
	}
	
	@ModelAttribute("listaPaises")
	public List<Pais> listaPaises() {
		/*
		return Arrays.asList(
				new Pais(1, "JP","Japón"),
				new Pais(2, "CO", "Colombia"),
				new Pais(3, "ES", "España"),
				new Pais(4, "MX", "México"), 
				new Pais(5, "CL", "Chile"),
				new Pais(6, "VE", "Venezuela"),
				new Pais(7, "AR", "Argentina"));
		*/
		return paisService.listar();
	}
	
	@ModelAttribute("paises")
	public List<String> paises() {
		return Arrays.asList("Japón", "Colombia", "España", "México", "Chile", "Venezuela");
	}

	@ModelAttribute("paisesMap")
	public Map<String, String> paisesMap() {
		Map<String, String> paises = new HashMap<>();
		
		paises.put("JP", "Japón");
		paises.put("CO", "Colombia");
		paises.put("ES", "España");
		paises.put("MX", "México");
		paises.put("CL", "Chile");
		paises.put("VE", "Venezuela");
		
		return paises;
	}
	
	@ModelAttribute("listaRolesString")
	public List<String> listaRolesString(){
		
		List<String> roles = new ArrayList<>();
		
		roles.add("ROLE_ADMIN");
		roles.add("ROLE_USER");
		roles.add("ROLE_MODERTATOR");
		
		return roles;
	}
	
	@ModelAttribute("listaRoles")
	public List<Role> listaRoles(){
		return this.roleService.listar();
	}
	
	@ModelAttribute("listaRolesMap")
	public Map<String, String> listaRolesMap() {
		Map<String, String> roles = new HashMap<>();
		
		roles.put("ROLE_ADMIN", "Administrador");
		roles.put("ROLE_USER", "Usuario");
		roles.put("ROLE_MODERTATOR", "Moderador");
		
		return roles;
	}
	
	@ModelAttribute("genero")
	public List<String> genero() {
		return Arrays.asList("Hombre", "Mujer");
	}
	
	/*
	 * NOTA: Método para mostrar el formulario en la pantalla
	 */
	@GetMapping("/form")
	public String form(Model model) {
		model.addAttribute("titulo", "Formulario Usuario");
		// En la primera creación del formulario para que no de error debemos crear un objeto usuario vacío
		// y pasarlo al formulario si no nos da un error NullPointerException.
		Usuario usuario = new Usuario();
		
		/*
		 * NOTA: Pasamos datos por defecto al formulario
		 */
		usuario.setNombre("Jhon");
		usuario.setApellido("Doe");
		
		// NOTA: Este campo como mencionamos en la clase Usuario no es un campo que es propio del formulario
		//       pero si queremos que persista y se muestre en el resutlado
		usuario.setIdentificador("123.456.789-Z");
		
		// Asignamos un valor por defecto al idRegex para ver las validaciones con expresiones regulares
		usuario.setIdRegex("987.654.321-X");
		
		// Habilitamos el checkbox por defecto
		usuario.setHabilitar(true);
		
		// Asignamos un valor por defecto al atributo hidden
		usuario.setValorSecreto("Algún valor secreto!!!...");
		
		usuario.setPais(new Pais(1, "JP", "Japón"));
		
		usuario.setRoles(Arrays.asList(new Role(2, "Usuario", "ROLE_USER")));
		
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
	 *                 
	 * NOTA: La interface SessionStatus nos va a servir para limpiar el objeto de la sesión que creamos para almacenar los valores del objeto usuario
	 *       cuando finalice el proceso, es decir, cuando se envíe el formulario de forma correcta.
	 */
	public String procesarFormulario(Model model, @Valid Usuario usuario, BindingResult result, SessionStatus status) {
		
		/*
		 *  NOTA: Comentamos esta línea de código para pasar el validador directamente y hacerlo de forma más
		 *        trasparente a través del @initBinder el cual es un método que anotado que se encarga inicializar
		 *        
		 *        // Usamos la instancia inyectada de la clase de validaciones personalizada
		 *        //validador.validate(usuario, result);
		 *        
		 */ 
		
		
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
			/*
			 * NOTA: Optimización de los errores para manejarlo de forma automática e implícita en thymeleaf 
			 *       y Spring Framework y no manual como lo tenemos acá. Por lo tanto comentamos este código
			 *       y en la vista en los div cambiamos por la implementación propia de thymeleaf con Spring
			 * 
			 * Map<String, String> errores = new HashMap<>();
			 * // Usamos el result para obtener el mensaje de error y vamos poblando el mapa
			 * // con los mensajes de error.
			 * // Recorremos por cada mensaje de error.
			 * result.getFieldErrors().forEach(err -> {
			 * // Y acá asignamos la llave (nombre del campo obtenido con getField) y el valor
			 * // es el mensaje de error que queremos manejar.
			 * errores.put(err.getField(), "El campo ".concat(err.getField().concat(" ").concat(err.getDefaultMessage())));
			 * });
			 * 
			 * // Pasamos a la vista los errores
			 * model.addAttribute("error", errores); 
			 */
			
			return "form";
			
		}
		
		// Completamos la sessión para que elimine de forma automática el objeto usuario creado en la sesión.
		status.setComplete();
		
		// NOTA: Se realizo el refactor para pasar directamente el objeto
		model.addAttribute("usuario", usuario);
		
		return "resultado";
	}
	

}
