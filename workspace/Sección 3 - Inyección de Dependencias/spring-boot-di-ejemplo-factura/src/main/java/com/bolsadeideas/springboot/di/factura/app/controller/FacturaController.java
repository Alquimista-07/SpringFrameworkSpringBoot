package com.bolsadeideas.springboot.di.factura.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bolsadeideas.springboot.di.factura.app.models.domain.Factura;

@Controller
@RequestMapping("/factura")
public class FacturaController {
	
	/*
	 * NOTA: Como queremos mostrar los datos de la factura con su respectivo detalle
	 *       entonces lo que tenemos que hacer es inyectarla.
	 */
	@Autowired
	private Factura factura;
	
	@GetMapping("/ver")
	public String ver(Model model) {
		/*
		 * NOTA: Pasamos a la vista la factura
		 */
		model.addAttribute("factura", factura);
		model.addAttribute("titulo", "Ejeplo Factura con inyección e dependencias");
		return "factura/ver";
	}

}
