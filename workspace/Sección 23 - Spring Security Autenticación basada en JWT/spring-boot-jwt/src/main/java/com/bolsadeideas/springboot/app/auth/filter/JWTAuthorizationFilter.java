package com.bolsadeideas.springboot.app.auth.filter;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		String header = request.getHeader("Authorization");
		
		if (!requeresAuthentication(header)) {
			// Si es distinto con la ejecución y salimos del filtro
			chain.doFilter(request, response);
			return;
		}
		
		boolean validToken;
		Claims token = null;
		
		// Implementamos la validación del token y para ello tenemos que invocar el método parse para analizar el token
		// a través de la misma clase del toen que es Jwts
		try {
			
			token = Jwts.parser()
			// Y pasamos la llave que es la misma que pasamos en el JWTAuthenticationFilter, pero luego se va a crear de forma global
			.setSigningKey("Alguna clave Secreta 1234567890".getBytes())
			// Lo siguiente es analizar, es validar, pero como sabemos viene con el prefijo Bearer el cual tenemos que quitar
			.parseClaimsJws(header.replace("Bearer ", "")).getBody();
			
			validToken = true;
		
		} catch (JwtException | IllegalArgumentException e) {
			validToken = false;
			e.printStackTrace();
		}
		
	}
	
	// Método para validar si requiere autenticación
	protected boolean requeresAuthentication(String header) {
		
		if (header == null || !header.startsWith("Bearer ")) {
			return false;
		}
		
		return true;
		
	}

}
