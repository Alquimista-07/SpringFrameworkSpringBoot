package com.bolsadeideas.springboot.app.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bolsadeideas.springboot.app.models.dao.IClienteDao;
import com.bolsadeideas.springboot.app.models.entity.Cliente;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("cliente")
public class ClienteController {
	
	// Inyectamos la interface DAO para el llamado a los métodos
	@Autowired
	@Qualifier("clienteDaoJPA")
	private IClienteDao clienteDao;
	
	// NOTA: Podemos anotar con @GetMapping o @RequestMapping, en este caso vamos a variar y anotar con @RequestMapping
	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de clientes");
		// Usamos el atributo que creamos y que hace referencia a la interface para llamar el método
		// y se lo pasamos a la vista para mostrarlo
		model.addAttribute("clientes", clienteDao.findAll());
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
	//       session attribute para no usar el input que teníamos para el id en la vista, luego de guardar tenemos que limpiar dicho sessión atribute, y para ello usamos el SessionStatus
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar( @Valid Cliente cliente, BindingResult result, Model model, SessionStatus status ) {
		
		if( result.hasErrors() ) {
			model.addAttribute("titulo", "Formulario de Cliente");
			return "form";
		}
		
		clienteDao.save(cliente);
		// Limpiamos el session atribute
		status.setComplete();
		return "redirect:listar";
	}
	
	// Metodo para editar, al cual le pasamos el id a través de un path variable
	@RequestMapping(value = "/form/{id}")
	public String editar( @PathVariable(value = "id") Long id, Map<String, Object> model ) {
		
		Cliente cliente = null;
		
		if ( id > 0 ) {
			// Buscamos en la base de datos
			cliente = clienteDao.findOne(id);
		} else {
			return "redirect:/listar";
		}
		
		model.put("cliente", cliente);
		model.put("titulo", "Editar Cliente");
		
		return "form";
	}

}
