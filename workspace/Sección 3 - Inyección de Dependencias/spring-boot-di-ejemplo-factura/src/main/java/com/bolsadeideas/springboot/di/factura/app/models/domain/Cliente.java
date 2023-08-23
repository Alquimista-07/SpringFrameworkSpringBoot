package com.bolsadeideas.springboot.di.factura.app.models.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
/* NOTA: Acá hacemos el cambio para que este componente no se Singleton (Instancia única) sino que
*       sea del tipo Request. Y por lo tanto ahora esta factura, este Bean va a durar lo que dura 
*       una petición HTTP de usuario, por lo tanto cada usuario que se conecte va a tener una factura
*       distinta y propia, por lo tanto si modificamos algún valor, dicho valor no se altara para el
*       resto de usuarios.
*/
@RequestScope
public class Cliente {

	// Atributos
	/*
	 * NOTA: El nombre y el apellido lo podemos guardar en un archivo properties,
	 * por lo tanto los inyectamos usando el decorador @Value para inyectar sus
	 * correspondientes valores.
	 */
	@Value("${cliente.nombre}")
	private String nombre;

	@Value("${cliente.apellido}")
	private String apellido;

	// Getters y Setters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

}
