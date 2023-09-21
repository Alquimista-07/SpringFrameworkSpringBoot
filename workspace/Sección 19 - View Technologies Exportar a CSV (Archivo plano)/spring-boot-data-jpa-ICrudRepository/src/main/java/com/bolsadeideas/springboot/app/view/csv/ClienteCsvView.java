package com.bolsadeideas.springboot.app.view.csv;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// NOTA: Pasamos la vista de clientes y de la misma forma que con pdf y xlsx hace referencia a la que paamos a la vista
//       en el model. Adicionalmente a diferencia de pdf y excel no tenemos una clase heredada específica por lo tanto acá 
//       tenemos que configurarla a partir de una más genérica
@Component("listar")
public class ClienteCsvView extends AbstractView {

	public ClienteCsvView() {
		setContentType("text/csv");
	}

	// Implementamos un método que genera contenido descargable
	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// Asignamos un nombre al archivo de salida
		response.setHeader("Content-Disposition", "attachment; filename=\"clientes.csv\"");
		
		// Pasamos el content type a la respuesta
		response.setContentType(getContentType());
		
	}

	
	
}
