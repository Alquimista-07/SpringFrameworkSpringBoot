package com.bolsadeideas.springboot.app.view.pdf;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.bolsadeideas.springboot.app.models.entity.Factura;
import com.bolsadeideas.springboot.app.models.entity.ItemFactura;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// NOTA: Acá tenemos que pasarle el mismo nombre que esta reotornando la vista en el controlador
//       en este caso lo que retorna el método ver en factura controller
@Component("factura/ver")
public class FacturaPdfView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// Tenemos que pasar en el get el mismo nombre con el que estamos pasando a la vista cuando hacemos el model.addAttribute
		Factura factura = (Factura) model.get("factura");
		
		// Una sola coluna y tres filas
		PdfPTable tabla = new PdfPTable(1);
		
		// Colocamos una separación entre tablas
		tabla.setSpacingAfter(20);
		
		tabla.addCell("Datos del cliente");
		tabla.addCell(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
		tabla.addCell(factura.getCliente().getEmail());
		
		PdfPTable tabla2 = new PdfPTable(1);
		
		// Colocamos una separación entre tablas
		tabla2.setSpacingAfter(20);
		
		tabla2.addCell("Folio: " + factura.getId());
		tabla2.addCell("Descripción: " + factura.getDescripcion());
		tabla2.addCell("Fecha: " + factura.getCreateAt());
		
		// Guardamos las tablas al documento
		document.add(tabla);
		document.add(tabla2);
		
		// Tabla para el detalle de la factura
		PdfPTable tabla3 = new PdfPTable(4);
		
		tabla3.addCell("Producto");
		tabla3.addCell("Precio");
		tabla3.addCell("Cantidad");
		tabla3.addCell("Total");
		
		for( ItemFactura item: factura.getItems() ) {
			tabla3.addCell(item.getProducto().getNombre());
			tabla3.addCell(item.getProducto().getPrecio().toString());
			tabla3.addCell(item.getCantidad().toString());
			tabla3.addCell(item.calcularImporte().toString());
		}
		
		// Para el subtotal creamos una sola celda
		PdfPCell cell = new PdfPCell(new Phrase("Total: "));
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
