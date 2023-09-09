package com.bolsadeideas.springboot.error.app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bolsadeideas.springboot.error.app.models.domain.Usuario;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	private List<Usuario> lista;

	public UsuarioServiceImpl() {
		this.lista = new ArrayList<>();
		this.lista.add(new Usuario(1, "Andrés", "Guzmán"));
		this.lista.add(new Usuario(2, "Pepa", "García"));
		this.lista.add(new Usuario(3, "Lalo", "Garza"));
		this.lista.add(new Usuario(4, "Luci", "Fernández"));
		this.lista.add(new Usuario(5, "Pato", "González"));
		this.lista.add(new Usuario(6, "Paco", "Rodríguez"));
		this.lista.add(new Usuario(7, "Julián", "Gómez"));
	}

	@Override
	public List<Usuario> listar() {
		return this.lista;
	}

	@Override
	public Usuario obtenerPorId(Integer id) {
		Usuario resultado = null;
		
		// NOTA: Acá igualamos con el méotod equals() ya que es un objeto de referencia, en este caso el id que 
		//       lo definimos como Integer pero si estuvieramos usando tipos primitivos, es decir, int igualaríamos
		//       con el signo =. Y eso aplica de la misma forma con los String.
		for (Usuario usuario : this.lista) {
			if (usuario.getId().equals(id)) {
				resultado = usuario;
				break;
			}
		}
		
		return resultado;
	}

	@Override
	public Optional<Usuario> obtenerPorIdOptional(Integer id) {
		Usuario usuario = this.obtenerPorId(id);
		// Acá con este of lo que hacemos es convertir el objeto usuario, independiente si es enulo o no
		// en un tipo optional y de este sale dos métodos, el of y el ofNullable. El of no acepta nulos 
		// y el ofNullable si los acepta
		return Optional.ofNullable(usuario);
	}

}
