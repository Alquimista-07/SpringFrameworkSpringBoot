package com.bolsadeideas.springboot.app.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.util.paginator.PageRender;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("cliente")
public class ClienteController {
	
	// Como realizamos el refactor para implementar la clase service ahora ya no inyectamos el DAO directamente
	// sino que inyectamos el servicio
	@Autowired
	@Qualifier("clienteServiceJPA")
	private IClienteService clienteService;
	
	// NOTA: Podemos anotar con @GetMapping o @RequestMapping, en este caso vamos a variar y anotar con @RequestMapping.
	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	// NOTA: Vamos a usar el request param para obtener la página actual y de esta forma llamar al nuevo método que creamos
	//       y que se encarga de la paginación.
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		
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
	public String guardar( @Valid Cliente cliente, BindingResult result, Model model, @RequestParam(name = "file") MultipartFile foto, RedirectAttributes flash, SessionStatus status ) {
		
		if( result.hasErrors() ) {
			model.addAttribute("titulo", "Formulario de Cliente");
			return "form";
		}
		
		// Validamos la foto y pasamos el path donde se van a guardar las imágenes
		if ( !foto.isEmpty() ) {
			Path directorioRecursos = Paths.get("src/main/resources/static/uploads");
			String rootPath = directorioRecursos.toFile().getAbsolutePath();
			
			try {
				// Obtenemos los bytes de la imágen
				byte[] bytes = foto.getBytes();
				
				// Obtenemos la ruta final con el nombre del archivo
				Path rutaCompleta = Paths.get(rootPath + "//" + foto.getOriginalFilename());
				
				// Gudardamos la foto
				Files.write(rutaCompleta, bytes);
				
				// Mandamos un mensaje al flash que creamos anteriormente
				flash.addFlashAttribute("info", "Ha subido correctamente '" + foto.getOriginalFilename() + "'");
				
				// Pasamos el nombre de la foto al objeto cliente para que quede guardado en la base de datos
				// y de esta forma poderla recuperar para mostrar o hacer alguna operación con ella.
				cliente.setFoto(foto.getOriginalFilename());
			} 
			
			catch (IOException e) {
				e.printStackTrace();
			}
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
	public String eliminar( @PathVariable(value = "id") Long id, RedirectAttributes flash ) {
		
		if ( id > 0 ) {
			clienteService.delete(id);
			flash.addFlashAttribute("success", "Cliente eliminado con éxito!");
		}
		
			return "redirect:/listar";
	}

}
