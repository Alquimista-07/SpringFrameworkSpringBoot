package com.bolsadeideas.springboot.app.view.xml;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.xml.MarshallingView;

import com.bolsadeideas.springboot.app.models.entity.Cliente;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("listar.xml")
public class ClienteListXmlView extends MarshallingView {

	// A través del constructor inyectamos el bean que se encarga de serializar el objeto en un 
	// documento xml
	@Autowired
	public ClienteListXmlView(Jaxb2Marshaller marshaller) {
		super(marshaller);
		// TODO Auto-generated constructor stub
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// Eliminamos lo que no nos sirve (titulo y page) del modelo ya que solo queremos los clientes
		model.remove("titulo");
		model.remove("page");
		
		// Adicionalmente también borramos los clientes pero antes de borrarlos obtenemos los clientes para hacer
		// la conversión
		Page<Cliente> clientes = (Page<Cliente>) model.get("clientes");
		model.remove("clientes");
		
		// Guardamos en el model el wraper ClienteList junto con el listado de clientes
		model.put("clienteList", new ClienteList(clientes.getContent()));
		
		super.renderMergedOutputModel(model, request, response);
	}
	
	

}
