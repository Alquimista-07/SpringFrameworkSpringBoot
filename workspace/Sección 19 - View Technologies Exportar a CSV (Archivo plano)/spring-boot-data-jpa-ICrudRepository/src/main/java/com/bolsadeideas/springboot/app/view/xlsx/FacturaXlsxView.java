package com.bolsadeideas.springboot.app.view.xlsx;

import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.support.MessageSourceAccessor;
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
		
		// Asginamos el nombre del archivo
		response.setHeader("Content-Disposition", "attachment; filename=\"factura_view.xlsx\"");

		// Obtenemos el objeto que se pasa por el model en la vista
		Factura factura = (Factura) model.get("factura");
				
		Sheet sheet = workbook.createSheet("Factura Spring");
		
		// Usamos uno de los métodos explicados con el pdf que es el más sencillo y que nos sirve para la traducción multilenguaje
		MessageSourceAccessor mensajes = getMessageSourceAccessor();
				
		// El parametro en este caso cero indica la primera posición de las celdas y filas
		// Primera fila
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue(mensajes.getMessage("text.factura.ver.datos.cliente"));
				
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
		sheet.createRow(4).createCell(0).setCellValue(mensajes.getMessage("text.factura.ver.datos.factura"));
		sheet.createRow(5).createCell(0).setCellValue(mensajes.getMessage("text.cliente.factura.folio") + ": " + factura.getId());
		sheet.createRow(6).createCell(0).setCellValue(mensajes.getMessage("text.cliente.factura.descripcion") + ": " + factura.getDescripcion());
		sheet.createRow(7).createCell(0).setCellValue(mensajes.getMessage("text.cliente.factura.fecha") + ": " + factura.getCreateAt());
		
		// Customizando la tabla
		// Agregamos bordes
		CellStyle theaderStyle = workbook.createCellStyle();
		theaderStyle.setBorderBottom(BorderStyle.MEDIUM);
		theaderStyle.setBorderTop(BorderStyle.MEDIUM);
		theaderStyle.setBorderRight(BorderStyle.MEDIUM);
		theaderStyle.setBorderLeft(BorderStyle.MEDIUM);
		// Asignamos el color y su patron lo colocamos como un color sólido 
		theaderStyle.setFillForegroundColor(IndexedColors.GOLD.index);
		theaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		CellStyle tbodyStyle = workbook.createCellStyle();
		// Agregamos bordes
		tbodyStyle.setBorderBottom(BorderStyle.THIN);
		tbodyStyle.setBorderTop(BorderStyle.THIN);
		tbodyStyle.setBorderRight(BorderStyle.THIN);
		tbodyStyle.setBorderLeft(BorderStyle.THIN);
		
		// Detalle de la factura
		Row header = sheet.createRow(9);
		header.createCell(0).setCellValue(mensajes.getMessage("text.factura.form.item.nombre"));
		header.createCell(1).setCellValue(mensajes.getMessage("text.factura.form.item.precio"));
		header.createCell(2).setCellValue(mensajes.getMessage("text.factura.form.item.cantidad"));
		header.createCell(3).setCellValue(mensajes.getMessage("text.factura.form.item.total"));
		
		// Aplicamos los estilos
		header.getCell(0).setCellStyle(theaderStyle);
		header.getCell(1).setCellStyle(theaderStyle);
		header.getCell(2).setCellStyle(theaderStyle);
		header.getCell(3).setCellStyle(theaderStyle);
		
		// Llenamos con los items
		// NOTA: El rowNum no puede arrancar en cero ya que si contamos las celdas creadas anteriormente este nos da que vamos en la posición 10
		int rowNum = 10;
		
		for ( ItemFactura item: factura.getItems() ) {
			Row fila = sheet.createRow(rowNum ++);
			
			// Pasamos los items con sus respectivos estilos
			cell = fila.createCell(0);
			cell.setCellValue(item.getProducto().getNombre());
			cell.setCellStyle(tbodyStyle);
			
			cell = fila.createCell(1);
			cell.setCellValue(item.getProducto().getPrecio());
			cell.setCellStyle(tbodyStyle);
			
			cell = fila.createCell(2);
			cell.setCellValue(item.getCantidad());
			cell.setCellStyle(tbodyStyle);
			
			cell = fila.createCell(3);
			cell.setCellValue(item.calcularImporte());
			cell.setCellStyle(tbodyStyle);
		}
		
		// Fila gran total
		Row filaTotal = sheet.createRow(rowNum);

		cell = filaTotal.createCell(2);
		// Colocamos un borde 
		cell.setCellValue(mensajes.getMessage("text.factura.form.total"));
		cell.setCellStyle(tbodyStyle);
		
		cell = filaTotal.createCell(3);
		// Colocamos un borde
		cell.setCellValue(factura.getTotal());
		cell.setCellStyle(tbodyStyle);
	}

}
