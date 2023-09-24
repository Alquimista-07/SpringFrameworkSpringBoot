package com.bolsadeideas.springboot.app.auth.service;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Claims;

public interface JWTService {
	
	// Método que se encarga de crear el token
	public String create(Authentication auth) throws IOException;
	
	// Método para validar el token
	public boolean validate(String token);

	// Método para obtener los claims
	public Claims getClaims(String token);
	
	// Método para obtener el username y los roles desde el token
	public String getUsername(String token);
	
	// Método para obtener los roles
	public Collection<? extends GrantedAuthority> getRoles(String token ) throws IOException;
	
	// Método para resolver el token
	public String resolve(String token);
}
