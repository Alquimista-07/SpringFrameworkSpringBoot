package com.bolsadeideas.springboot.app.view.csv;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.bolsadeideas.springboot.app.models.entity.Cliente;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// NOTA: Esta librería no solo sirve para escribir sino también para leer, por lo tanto ejemplos y demás los podemos encontrar
//       en la documentación oficial en el siguiente enlace: 
//       https://super-csv.github.io/super-csv/

// NOTA: Pasamos la vista de clientes y de la misma forma que con pdf y xlsx hace referencia a la que paamos a la vista
//       en el model. Adicionalmente a diferencia de pdf y excel no tenemos una clase heredada específica por lo tanto acá 
//       tenemos que configurarla a partir de una más genérica.
//      
//       Adicionalmente como se comento anteriormente un componente solo puede tener un nombre, por lo tanto al igual que se realizo
//       con la generación de pdf y excel que usan la misma vista, nos dimos cuenta que podemos pasar una extensión y de esta forma 
//       evitamos el inconveniente de usar una misma vista en varios componentes. OJO hay que tener en cuenta que si manejamos de esta
//       forma con la extensión tenemos que configurar el contentngotiation.media-types en el application properties, ya que si no hacemos
//       esto no va a arrojar error pero tampoco va a generar el archivo. Para saber el media type lo podmos buscar en google como por ejemplo
//       "Media type o MIME type csv java"
@Component("listar.csv")
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
	// Omitimos el warning
	@SuppressWarnings("unchecked")
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// Asignamos un nombre al archivo de salida
		response.setHeader("Content-Disposition", "attachment; filename=\"clientes.csv\"");
		
		// Pasamos el content type a la respuesta
		response.setContentType(getContentType());
		
		// Obtenemos los clientes que pasamos a la vista como un paginable
		Page<Cliente> clientes = (Page<Cliente>) model.get("clientes");
		
		// Convertimos a texto plano y lo guardamos en la respuesta. Adicionalmente lo pasamos con STANDARD_PREFERENCE 
		// que indica que es separado por coma, pero tenemos otras configutaciones que podemos ver haciendo Ctrl + click
		// sobre la clase CsvPreference
		ICsvBeanWriter beanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		
		// Creamos un arreglo que tenga los valores de los atributos
		String[] header = {"id", "nombre", "apellido", "email", "createAt"};
		
		// Escribimos la línea del header
		beanWriter.writeHeader(header);
		
		// Recorremos con un ciclo los clientes para guardar cada objeto en el archivo plano
		for( Cliente cliente: clientes ) {
			beanWriter.write(cliente, header);
		}
		
		// Cerramos el recurso
		beanWriter.close();
	}
	
}
