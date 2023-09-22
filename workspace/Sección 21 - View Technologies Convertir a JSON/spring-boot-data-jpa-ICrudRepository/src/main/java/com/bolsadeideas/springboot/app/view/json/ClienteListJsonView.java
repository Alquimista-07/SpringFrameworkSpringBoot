package com.bolsadeideas.springboot.app.view.json;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.bolsadeideas.springboot.app.models.entity.Cliente;

// NOTA: En este caso para la generación de objetos JSON no es necesaria ninguna dependencia
//       ya que este viene configurado por defecto en la dependencia spring-boot-starter-web.
//       Adicionalmente a diferencia de XML acá no tenemos que crear una clase warapper.
//       Y de la misma forma que con xml, pdf, xlsx es necesario asignar el nombre y que corresponde
//       al mismo nombre de la vista.
@Component("listar.json")
public class ClienteListJsonView extends MappingJackson2JsonView {

	@SuppressWarnings("unchecked")
	@Override
	protected Object filterModel(Map<String, Object> model) {
		
		// De la misma manera que con XML es necesario filtrar y eliminar algunas cosas del model
		model.remove("titulo");
		model.remove("page");
		
		// Adicionalmente como el objeto json sale con pageable, content y demás cosas que no nos interesa
		// ya que solo queremos los clientes, por lo tanto es necesario realizar un filtrado
		Page<Cliente> clintes = (Page<Cliente>) model.get("clientes");
		model.remove("clientes");
		model.put("clientes", clintes.getContent());
		
		return super.filterModel(model);
	}
	
	

}
