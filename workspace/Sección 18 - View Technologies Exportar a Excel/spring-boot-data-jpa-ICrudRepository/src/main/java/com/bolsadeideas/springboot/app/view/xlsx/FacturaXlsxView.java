package com.bolsadeideas.springboot.app.view.xlsx;

import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.annotations.Comment;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// NOTA: La documentación la podemos encontrar en:
//       https://poi.apache.org/components/index.html
//       https://mvnrepository.com/search?q=poi

// NOTA: Esta es la clase de vista para la vista en Excel y de la misma manera que realizamos con el
//       PDF tenemos que pasar la vista que hace referencia en el Model para el objeto, pero como dos
//       vistas no pueden llamarse igual hacemos una pequeña distinción agregando por ejemplo la extensión
//       y mantenemos una parte de la ruta original.
@Comment("factura/ver.xlsx")
public class FacturaXlsxView extends AbstractXlsxView {

	// Muy similar a lo que teníamos con pdf agregamos el método sobre cargado de la clase heredada y es sobre el cual vamos a tabajar
	// y nos va a permitir hacer todo respecto a configuración, presentación y demás.
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		
		
	}

}
