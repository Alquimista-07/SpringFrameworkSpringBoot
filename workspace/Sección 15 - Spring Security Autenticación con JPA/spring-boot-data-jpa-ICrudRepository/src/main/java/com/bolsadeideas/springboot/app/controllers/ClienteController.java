package com.bolsadeideas.springboot.app.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.app.models.dao.service.IClienteService;
import com.bolsadeideas.springboot.app.models.dao.service.IUploadFileService;
import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.util.paginator.PageRender;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@SessionAttributes("cliente")
public class ClienteController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	// Como realizamos el refactor para implementar la clase service ahora ya no inyectamos el DAO directamente
	// sino que inyectamos el servicio
	@Autowired
	@Qualifier("clienteServiceJPA")
	private IClienteService clienteService;
	
	// Inyectamos la calse de servicio para la carga de archivos
	@Autowired
	private IUploadFileService uploadFileService;
	
	// NOTA: Podemos anotar con @GetMapping o @RequestMapping, en este caso vamos a variar y anotar con @RequestMapping.
	@RequestMapping(value = {"/listar", "/"}, method = RequestMethod.GET)
	// NOTA: Vamos a usar el request param para obtener la página actual y de esta forma llamar al nuevo método que creamos
	//       y que se encarga de la paginación.
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model, Authentication authentication, HttpServletRequest request) {
		
		// Una forma de obtener el usuario autenticado
		if ( authentication != null ) {
			logger.info("Hola usuario autenticado, tu username es: ".concat(authentication.getName()));
		}
		
		// Otra forma de obtener el usuario autenticado
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if ( auth != null ) {
			logger.info("Utilizando formá estática SecurityContextHolder.getContext().getAuthentication(): Usuario autenticado, username es: ".concat(auth.getName()));
		}
		
		// Validamos el role de varias formas (Los siguientes tres if-else)
		//----------------------------------------------------------------------------------------
		if( hasRole("ROLE_ADMIN") ) {
			logger.info("Hola ".concat(auth.getName().concat(" tienes acceso!")));
		}
		else {
			logger.info("Hola ".concat(auth.getName().concat(" NO tienes acceso!")));
		}
		//----------------------------------------------------------------------------------------
		
		SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request, "ROLE_");
		
		if( securityContext.isUserInRole("ADMIN") ) {
			logger.info("Forma usando SecurityContextHolderAwareRequestWrapper: Hola ".concat(auth.getName().concat(" tienes acceso!")));
		}
		else {
			logger.info("Forma usando SecurityContextHolderAwareRequestWrapper: Hola ".concat(auth.getName().concat(" NO tienes acceso!")));
		}
		//----------------------------------------------------------------------------------------
		
		if( request.isUserInRole("ROLE_ADMIN") ) {
			logger.info("Forma usando HttpServletRequest: Hola ".concat(auth.getName().concat(" tienes acceso!")));
		}
		else {
			logger.info("Forma usando HttpServletRequest: Hola ".concat(auth.getName().concat(" NO tienes acceso!")));
		}
		//----------------------------------------------------------------------------------------
		
		// Creamos el objeto pageable para la paginación al cual le pasamos como primer parametro
		// la página actual y como segundo la cantidad de elementos a mostrar por página.
		Pageable pageRequest = PageRequest.of(page, 4);
		
		// Invocamos el servide findAll pero paginable, es decir el nuevo método que agregamos en la
		// interface IClienteService
		Page<Cliente> clientes = clienteService.findAll(pageRequest);
		
		// Llamamos nuesto page render generico ya que sirve para paginar cualquier objeto no solo clientes.
		// Y el cual recibe la url y lo que devuelve el método findAll()
		PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);

		model.addAttribute("titulo", "Listado de clientes");
		// Usamos el atributo que creamos y que hace referencia a la interface para llamar el método
		// y se lo pasamos a la vista para mostrarlo
		
		// NOTA ACTUALIZACIÓN: Comentamos esto para cambiar por el atributo que tiene los datos paginados
		//model.addAttribute("clientes", clienteService.findAll());
		model.addAttribute( "clientes", clientes );
		
		// Agregamos el paginador a la vista
		model.addAttribute( "page", pageRender );
		
		return "listar";
	}
	
	// Metodo para mostrar el formulario
	@RequestMapping(value = "/form", method = RequestMethod.GET)
	// NOTA: Acá no usamos el Model convencional para pasar datos a la vista sino que usamos un map
	//       pero funcionan igual, es decir, son para pasar datos a la vista.
	// NOTA: Usamos anotaciones para aplicar la seguridad ya que lo que teníamos en la clase SpringSecurityConfig
	//       para la protección de las rutas lo comentamos. Adicionalmente podemos pasar más de un role como un arreglo
	//       entre {} separado por comas
	@Secured({"ROLE_ADMIN"})
	public String crear(Map<String, Object> model) {
		
		Cliente cliente = new Cliente();
		
		model.put("cliente", cliente);
		model.put("titulo", "Formulario de Cliente");
		return "form";
	}
	
	// Método para procesar o enviar los datos del formulario 
	
	// NOTA: Ya que cuando pasamos el objeto cliente a la vista usando el model.put("cliente", cliente)estamos pasandolo con el mismo nombre, no es necesario indicarlo ya que por 
	//       defecto esto lo maneja el framework, en caso de que por ejemplo lo pasaramos como clienteOtro, (Ejemplo: model.put("clienteOtro", cliente)) si sería necesario 
	//       especificarlo usando como parámetro la anotación @ModelAttribute("clienteOtro") y como nos damos cuenta pasamos entre los paréntesis el nombre con el cual lo pasamo, 
	//       pero para este caso no es necesario ya que como se mención lo estamos pasando con el mismo nombre del objeto a la vista, adicionalmente como almacenamos el objeto en el
	//       session attribute para no usar el input que teníamos para el id en la vista, luego de guardar tenemos que limpiar dicho sessión atribute, y para ello usamos el SessionStatus.
	//       Para la subida de archivos agregamos otro parámtro que esl el @RequestParam
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	// NOTA: Usamos anotaciones para aplicar la seguridad ya que lo que teníamos en la clase SpringSecurityConfig
	//       para la protección de las rutas lo comentamos.Adicionalmente podemos pasar más de un role como un arreglo
	//       entre {} separado por comas
	@Secured("ROLE_ADMIN")
	public String guardar( @Valid Cliente cliente, BindingResult result, Model model, @RequestParam(name = "file") MultipartFile foto, RedirectAttributes flash, SessionStatus status ) {
		
		if( result.hasErrors() ) {
			model.addAttribute("titulo", "Formulario de Cliente");
			return "form";
		}
		
		// Validamos la foto y pasamos el path donde se van a guardar las imágenes
		if ( !foto.isEmpty() ) {
			
			// Para el reemplazo y borrado de la imágen para que no ocupe espacio cuando se edita un cliente hacemos la siguiente validación
			if ( cliente.getId() != null && cliente.getId() > 0 && cliente.getFoto() != null && cliente.getFoto().length() > 0 ) {
				// Borramos la foto antigua
				
				uploadFileService.delete(cliente.getFoto());
				
			}
			
			String uniqueFilename = null;
			
			try {
				uniqueFilename = uploadFileService.copy(foto);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Mandamos un mensaje al flash que creamos anteriormente
			//flash.addFlashAttribute("info", "Ha subido correctamente '" + foto.getOriginalFilename() + "'");
			flash.addFlashAttribute("info", "Ha subido correctamente '" + uniqueFilename + "'");
			
			// Pasamos el nombre de la foto al objeto cliente para que quede guardado en la base de datos
			// y de esta forma poderla recuperar para mostrar o hacer alguna operación con ella.
			//cliente.setFoto(foto.getOriginalFilename());
			cliente.setFoto(uniqueFilename);
			
		}
		else {
			cliente.setFoto("");
		}
		
		String mensajeFlash = (cliente.getId() != null) ? "Cliente editado con éxito!" : "Cliente creado con éxito!";
		
		clienteService.save(cliente);
		// Limpiamos el session atribute
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:listar";
	}
	
	// Metodo para editar, al cual le pasamos el id a través de un path variable
	@RequestMapping(value = "/form/{id}")
	// NOTA: La anotación preAutorize() es una alternativa a la anotación @Secured() y algo importante es que 
	//       para poderla usar es necesario pasar el parámentro prePostEnabled = true indicandola en la anotación 
	//       @EnableMethodSecurity() implementada en la clase SpringSecurityConfig para habilitarla. Adicionalmente
	//       podemos pasar más de un role usando el hasAnyRole y separando por coma. 
	//       
	//       Ejemplo: 
	//       @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_OTRO')")
	//
	@PreAuthorize("hasRole('ROLE_USER')")
	public String editar( @PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash ) {
		
		Cliente cliente = null;
		
		if ( id > 0 ) {
			// Buscamos en la base de datos
			cliente = clienteService.findOne(id);
			if (cliente == null ) {
				flash.addFlashAttribute("error", "El id del cliente no existe en la base de datos");
				return "redirect:/listar";
			}
		} else {
			flash.addFlashAttribute("error", "Ha ocurrido un error al editar el cliente, el id no puede ser cero");
			return "redirect:/listar";
		}
		
		model.put("cliente", cliente);
		model.put("titulo", "Editar Cliente");
		
		return "form";
	}
	
	@RequestMapping(value = "/eliminar/{id}")
	// NOTA: Usamos anotaciones para aplicar la seguridad ya que lo que teníamos en la clase SpringSecurityConfig
	//       para la protección de las rutas lo comentamos.
	@Secured("ROLE_ADMIN")
	public String eliminar( @PathVariable(value = "id") Long id, RedirectAttributes flash ) {
		
		if ( id > 0 ) {
			
			Cliente cliente = clienteService.findOne(id);
			
			clienteService.delete(id);
			flash.addFlashAttribute("success", "Cliente eliminado con éxito!");
			
			// Se elimina
			if ( uploadFileService.delete(cliente.getFoto()) ) {
				flash.addFlashAttribute("info", "Foto " + cliente.getFoto() + " eliminada con éxito!");
			}
		}
		
			return "redirect:/listar";
	}
	
	// Método para ver el detalle y la foto del cliente
	// el cual va a tener un path variable
	@RequestMapping(value = "/ver/{id}", method = RequestMethod.GET )
	// NOTA: La anotación preAutorize() es una alternativa a la anotación @Secured() y algo importante es que 
	//       para poderla usar es necesario pasar el parámentro prePostEnabled = true indicandola en la anotación 
	//       @EnableMethodSecurity() implementada en la clase SpringSecurityConfig para habilitarla. Adicionalmente
	//       podemos pasar más de un role usando el hasAnyRole y separando por coma. 
	//       
	//       Ejemplo: 
	//       @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_OTRO')")
	//
	@PreAuthorize("hasRole('ROLE_USER')")
	public String ver(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		
		// Cliente cliente = clienteService.findOne(id);
		Cliente cliente = clienteService.fetchByIdWithFacturas(id);
		
		if (cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:/listar";
		}
		
		model.put("cliente", cliente);
		model.put("titulo", "Detalle cliente: " + cliente.getNombre());
		
		return "ver";
	}
	
	// Creamos un nuevo método que corresponde a la cuarta forma de subir archivos 
	// El .+ evita que la extensión (jpg, png, etc) del archivo se trunque y sea eliminada
	@RequestMapping(value = "/uploads/{filename:.+}", method = RequestMethod.GET)
	// NOTA: Ahora vamos a usar anotaciones para aplicar la seguridad y no como teníamos en la clase SpringSecurityConfig que comentamos
	@Secured("ROLE_USER")
	public ResponseEntity<Resource> verFoto ( @PathVariable String filename ) {
		
		Resource recurso = null;
		
		try {
			recurso = uploadFileService.load(filename);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ recurso.getFilename() +"\"")
				.body(recurso);
	}
	
	// Método para validar roles y verificar permisos
	private boolean hasRole(String role) {
		
		// Obtenemos el objeto authentication de forma estática
		SecurityContext context = SecurityContextHolder.getContext();
		
		if( context == null ) {
			return false;
		}
		
		// Obtenemos el authentication
		Authentication auth = context.getAuthentication();
		
		if( auth == null ) {
			return false;
		}
		
		// Obtenemos la colección de roles
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		
		//-------------------------------------
		// Primera forma
		//-------------------------------------
		/*
		// Iteramos para preguntar por cada authority para preguntar si el role que estamos pasando por 
		// parámetro es igual al role
		for ( GrantedAuthority authority : authorities ) {
			if( role.equals(authority.getAuthority()) ) {
				// Si se cumple tiene el role por lo tanto tiene permiso
				logger.info("Hola usuario".concat(auth.getName().concat(" tu role es: ".concat(authority.getAuthority()))));
				return true;
			}
		}
		
		// No tiene acceso no tiene role
		return false;
		*/
		
		//-------------------------------------
		// Segunda forma sin iterar
		//-------------------------------------
		return authorities.contains(new SimpleGrantedAuthority(role));
	}

}
