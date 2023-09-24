package com.bolsadeideas.springboot.app.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SimpleGrantedAuthorityMixin {

	@JsonCreator
	// Ahora indicamos cual atributo del json contiene el valor del role
	// a través de la anotación @JsonProperty
	public SimpleGrantedAuthorityMixin(@JsonProperty("authority") String role) {
		
	}
	
	
}
