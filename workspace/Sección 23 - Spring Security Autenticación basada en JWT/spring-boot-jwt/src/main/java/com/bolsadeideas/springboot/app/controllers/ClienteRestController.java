package com.bolsadeideas.springboot.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bolsadeideas.springboot.app.models.dao.service.IClienteService;
import com.bolsadeideas.springboot.app.view.xml.ClienteList;

//NOTA: Ahora que pasa si quiero un controlador específico y que este dedicado 100% a un API REST
//      en este caso en vez de anotar con @Controller tenemos que anotar con @RestController y
//      esta es una anotación que combina dos anotaciones ya que esta formada por el esterotipo
//      Controller más ResponseBody y por lo tanto todos los métodos va a ser ResponseBody, y
//      todos su métodos handler van a responder a una aplicación del tipo REST

@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {

	@Autowired
	private IClienteService clienteService;
	
	// NOTA: Y como se mencionó como el controlador ya tiene el ResponseBody lo podemos omitir y no anotar 
	//       el método a diferencia de un controllador normal anotado con @Controller.
	//       Para llamar este endpoint también le pasamos el formato JSON o XML:
	//       http://localhost:8080/api/clientes/listar?format=json
	@GetMapping("/listar")
	// Para hacer una prueba colocamos que los unicos que pueden ver la información son los admin
	@Secured("ROLE_ADMIN")
	public ClienteList listar() {
		return new ClienteList(clienteService.findAll());
	}
	
	
}
