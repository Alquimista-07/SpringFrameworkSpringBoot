package com.bolsadeideas.springboot.app.view.xlsx;

import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.bolsadeideas.springboot.app.models.entity.Factura;
import com.bolsadeideas.springboot.app.models.entity.ItemFactura;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// NOTA: La documentación la podemos encontrar en:
//       https://poi.apache.org/components/index.html
//       https://mvnrepository.com/search?q=poi

// NOTA: Esta es la clase de vista para la vista en Excel y de la misma manera que realizamos con el
//       PDF tenemos que pasar la vista que hace referencia en el Model para el objeto, pero como dos
//       vistas no pueden llamarse igual hacemos una pequeña distinción agregando por ejemplo la extensión
//       y mantenemos una parte de la ruta original.
@Component("factura/ver.xlsx")
public class FacturaXlsxView extends AbstractXlsxView {

	// Muy similar a lo que teníamos con pdf agregamos el método sobre cargado de la clase heredada y es sobre el cual vamos a tabajar
	// y nos va a permitir hacer todo respecto a configuración, presentación y demás.
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// Obtenemos el objeto que se pasa por el model en la vista
		Factura factura = (Factura) model.get("factura");
				
		Sheet sheet = workbook.createSheet("Factura Spring");
				
		// El parametro en este caso cero indica la primera posición de las celdas y filas
		// Primera fila
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue("Datos del cliente");
				
		// Segunda fila
		row = sheet.createRow(1);
		cell = row.createCell(0);
		cell.setCellValue(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
				
		// Tercera fila
		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellValue(factura.getCliente().getEmail());
				
		// Otra forma más sencilla de hacer lo mismo que antes y crear filas
		// es encadenando métodos, para efectos ilustrativos lo vamos a hacer
		// de esta forma
		sheet.createRow(4).createCell(0).setCellValue("Datos de la factura");
		sheet.createRow(5).createCell(0).setCellValue("Folio: " + factura.getId());
		sheet.createRow(6).createCell(0).setCellValue("Descripción: " + factura.getDescripcion());
		sheet.createRow(7).createCell(0).setCellValue("Fecha: " + factura.getCreateAt());
		
		// Detalle de la factura
		Row header = sheet.createRow(9);
		header.createCell(0).setCellValue("Producto");
		header.createCell(1).setCellValue("Precio");
		header.createCell(2).setCellValue("Cantidad");
		header.createCell(3).setCellValue("Total");
		
		// Llenamos con los items
		// NOTA: El rowNum no puede arrancar en cero ya que si contamos las celdas creadas anteriormente este nos da que vamos en la posición 10
		int rowNum = 10;
		
		for ( ItemFactura item: factura.getItems() ) {
			Row fila = sheet.createRow(rowNum ++);
			fila.createCell(0).setCellValue(item.getProducto().getNombre());
			fila.createCell(1).setCellValue(item.getProducto().getPrecio());
			fila.createCell(2).setCellValue(item.getCantidad());
			fila.createCell(3).setCellValue(item.calcularImporte());
		}
		
		// Fila gran total
		Row filaTotal = sheet.createRow(rowNum);
		filaTotal.createCell(2).setCellValue("Gran Total");
		filaTotal.createCell(3).setCellValue(factura.getTotal());
	}

}
