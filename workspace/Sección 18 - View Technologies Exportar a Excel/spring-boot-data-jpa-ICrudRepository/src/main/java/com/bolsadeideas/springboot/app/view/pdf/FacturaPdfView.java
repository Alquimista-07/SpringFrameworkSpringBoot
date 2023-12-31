package com.bolsadeideas.springboot.app.view.pdf;

import java.awt.Color;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.bolsadeideas.springboot.app.models.entity.Factura;
import com.bolsadeideas.springboot.app.models.entity.ItemFactura;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// NOTA: La documentación la podemos encontrar en: https://itextpdf.com/itext-suite-java

// NOTA: Acá tenemos que pasarle el mismo nombre que esta reotornando la vista en el controlador
//       en este caso lo que retorna el método ver en factura controller
@Component("factura/ver")
public class FacturaPdfView extends AbstractPdfView {
	
	// Inyectamos las interfaces MessageSource y LocaleResolver que nos permiten manejar el multilenguaje con el fin de traducir la
	// factura a los idiomas previamente definidos.
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private LocaleResolver localeResolver;

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// Tenemos que pasar en el get el mismo nombre con el que estamos pasando a la vista cuando hacemos el model.addAttribute
		Factura factura = (Factura) model.get("factura");
		
		// Locale Resolver necesario para el multilenguaje
		Locale locale = localeResolver.resolveLocale(request);
		
		// NOTA: Una segunda forma para traducir es usar únicamente el MessageSourceAccessor, el cual por debajo ya tiene
		//       el locale y lo demás, entonces para fines ilustrativos vamos a usarlo y traducir el resto de la factura
		//       como lo que es el folio, descripción, fecha y demás usándolo.
		MessageSourceAccessor mensajes = getMessageSourceAccessor();
		
		// Una sola coluna y tres filas
		PdfPTable tabla = new PdfPTable(1);
		
		// Colocamos una separación entre tablas
		tabla.setSpacingAfter(20);
		
		// Definimos una celda
		PdfPCell cell = null;
		// Customizamos la celda 
		// cell = new PdfPCell(new Phrase("Datos del cliente"));
		// Le pasamos la traducción del texto unsando la interface messageSource
		cell = new PdfPCell(new Phrase(messageSource.getMessage("text.factura.ver.datos.cliente", null, locale)));
		cell.setBackgroundColor(new Color(184, 218, 255));
		cell.setPadding(8f);
		
		tabla.addCell(cell);
		tabla.addCell(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
		tabla.addCell(factura.getCliente().getEmail());
		
		PdfPTable tabla2 = new PdfPTable(1);
		
		// Colocamos una separación entre tablas
		tabla2.setSpacingAfter(20);
		
		//cell = new PdfPCell(new Phrase("Datos de la factura"));
		// Le pasamos la traducción del texto unsando la interface messageSource
		cell = new PdfPCell(new Phrase(messageSource.getMessage("text.factura.ver.datos.factura", null, locale)));
		cell.setBackgroundColor(new Color(195, 230, 203));
		cell.setPadding(8f);
		
		tabla2.addCell(cell);
		//tabla2.addCell("Folio: " + factura.getId());
		//tabla2.addCell("Descripción: " + factura.getDescripcion());
		//tabla2.addCell("Fecha: " + factura.getCreateAt());
		// Traducción a multilenguaje
		tabla2.addCell(mensajes.getMessage("text.cliente.factura.folio") + ": " + factura.getId());
		tabla2.addCell(mensajes.getMessage("text.cliente.factura.descripcion") + ": " + factura.getDescripcion());
		tabla2.addCell(mensajes.getMessage("text.cliente.factura.fecha") + ": " + factura.getCreateAt());
		
		// Guardamos las tablas al documento
		document.add(tabla);
		document.add(tabla2);
		
		// Tabla para el detalle de la factura
		PdfPTable tabla3 = new PdfPTable(4);
		
		// Cambiamos el tamaño de la letra de los productos.
		// y como parametro le pasamos un arreglo en el cual cada
		// posición indica una columna, es decir, la primera es de
		// 2,5, la siguiente de 1 y así sucesivamente.
		tabla3.setWidths(new float[] {3.5f, 1, 1, 1});
		
		// Aplicamos el multilenguaje
		//tabla3.addCell("Producto");
		//tabla3.addCell("Precio");
		//tabla3.addCell("Cantidad");
		//tabla3.addCell("Total");
		tabla3.addCell(mensajes.getMessage("text.factura.form.item.nombre"));
		tabla3.addCell(mensajes.getMessage("text.factura.form.item.precio"));
		tabla3.addCell(mensajes.getMessage("text.factura.form.item.cantidad"));
		tabla3.addCell(mensajes.getMessage("text.factura.form.item.total"));
		
		for( ItemFactura item: factura.getItems() ) {
			tabla3.addCell(item.getProducto().getNombre());
			tabla3.addCell(item.getProducto().getPrecio().toString());
			
			// Centramos la cantidad
			cell = new PdfPCell(new Phrase(item.getCantidad().toString()));
			cell.setHorizontalAlignment(PdfCell.ALIGN_CENTER);
			
			tabla3.addCell(cell);
			tabla3.addCell(item.calcularImporte().toString());
		}
		
		// Para el subtotal creamos una sola celda
		//cell = new PdfPCell(new Phrase("Total: "));
		// Pasamos la traducción a multilenguaje
		cell = new PdfPCell(new Phrase(mensajes.getMessage("text.factura.form.total")));
		
		// Asignamos que ocupe 3 columnas
		cell.setColspan(3);
		
		// Alineamos a la derecha
		cell.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
		
		// Agregamos a la tabla
		tabla3.addCell(cell);
		
		// Agregamos el gran total
		tabla3.addCell(factura.getTotal().toString());
		
		// Agregamos la tabla al documento
		document.add(tabla3);
	}
	
	

}
