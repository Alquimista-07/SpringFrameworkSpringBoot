package com.bolsadeideas.springboot.form.app.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bolsadeideas.springboot.form.app.models.domain.Pais;

@Service
public class PaisServiceImplement implements PaisService {
	
	private List<Pais> lista;

	public PaisServiceImplement() {
		this.lista = Arrays.asList(
				new Pais(1, "JP","Japón"),
				new Pais(2, "CO", "Colombia"),
				new Pais(3, "ES", "España"),
				new Pais(4, "MX", "México"), 
				new Pais(5, "CL", "Chile"),
				new Pais(6, "VE", "Venezuela"),
				new Pais(7, "AR", "Argentina"));
	}

	@Override
	public List<Pais> listar() {
		return lista;
	}

	@Override
	public Pais obtenerPorId(Integer id) {
		
		Pais resultado = null;
		
		for (Pais pais: this.lista) {
			if( id == pais.getId() ) {
				resultado = pais;
				break;
			}
		}
		return resultado;
	}

}
