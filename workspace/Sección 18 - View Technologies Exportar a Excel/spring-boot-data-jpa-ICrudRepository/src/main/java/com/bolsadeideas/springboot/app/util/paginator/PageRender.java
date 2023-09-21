package com.bolsadeideas.springboot.app.util.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public class PageRender<T> {
	
	private String url;
	private Page<T> page;
	
	private int totalPaginas;
	
	private int numElementosPorPagina;
	
	private int paginaActual;
	
	// Vamos a tener una colección ya que son varias páginas
	private List<PageItem> paginas;
	
	public PageRender(String url, Page<T> page) {
		this.url = url;
		this.page = page;
		
		// Inicializamos la colección de paginas
		this.paginas = new ArrayList<PageItem>();
		
		// Este atributo lo obtenemos por la inicialización que realizamos en el controlador
		// cuando llamamos al méotod PageRequest.of() y que indicamos que eran 4
		numElementosPorPagina = page.getSize();
		
		totalPaginas = page.getTotalPages();
		
		// Obtenemos la página actual y el cual indicamos que inicia en 0 cuando pasamos el defaulValue en el RequestParam
		// del método listar en el controlador ClienteController y le sumamos 1 para que se muestre de forma correcta en 
		// la vista
		paginaActual = page.getNumber() + 1;
		
		// Calculamos el desde y hasta para poder dibujar el paginador en la vista
		int desde, hasta;
		
		if ( totalPaginas <= numElementosPorPagina ) {
			desde = 1;
			hasta = totalPaginas;
		} else {
			// Navegamos por rango para que por ejemplo vaya de 10 en 10
			if ( paginaActual <= numElementosPorPagina/2 ) {
				desde = 1;
				hasta = numElementosPorPagina;
			} 
			// Calculamos el rango final
			else if ( paginaActual >= totalPaginas - numElementosPorPagina/2 ) {
				desde = totalPaginas - numElementosPorPagina + 1;
				hasta = numElementosPorPagina;
			}
			else {
				// Si no se cumple ni el rango inicial ni el final significa que estamos en el medio
				desde = paginaActual - numElementosPorPagina/2;
				hasta = numElementosPorPagina;
			}
		}
	
		// Recorremos el hasta para empezar a llenar las páginas con cada uno de los item
		// con su número y si es actual o no
		for ( int i = 0; i < hasta; i ++ ) {
			paginas.add(new PageItem(desde + i, paginaActual == desde + i));
		}
	}

	public String getUrl() {
		return url;
	}

	public int getTotalPaginas() {
		return totalPaginas;
	}

	public int getPaginaActual() {
		return paginaActual;
	}

	public List<PageItem> getPaginas() {
		return paginas;
	}
	
	// Métodos que creamos para saber si la primera pagina, o la última,
	// si tiene paginas siguientes o atrás.
	public boolean isFirst() {
		return page.isFirst();
	}
	
	public boolean isLast() {
		return page.isLast();
	}
	
	public boolean isHasNext() {
		return page.hasNext();
	}
	
	public boolean isHasPrevious() {
		return page.hasPrevious();
	}

}
