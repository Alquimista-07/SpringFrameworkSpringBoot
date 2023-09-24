package com.bolsadeideas.springboot.app.auth.filter;

import java.io.IOException;
//import java.util.Arrays;
//import java.util.Collection;

//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

//import com.bolsadeideas.springboot.app.auth.SimpleGrantedAuthorityMixin;
import com.bolsadeideas.springboot.app.auth.service.JWTService;
//import com.fasterxml.jackson.databind.ObjectMapper;
import com.bolsadeideas.springboot.app.auth.service.JWTServiceImpl;

//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.JwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	
	// Inyectamos el servicio JWT que creamos para pasarlo a través del constructor e inicializarla
	private JWTService jwtService;

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTService jwtService) {
		super(authenticationManager);
		this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		// Usamos la constante que definimos de forma global en el JWTServiceImpl
		String header = request.getHeader(JWTServiceImpl.HEADER_STRING);
		
		if (!requeresAuthentication(header)) {
			// Si es distinto con la ejecución y salimos del filtro
			chain.doFilter(request, response);
			return;
		}
		
		UsernamePasswordAuthenticationToken authentication = null;
		
		if (jwtService.validate(header)) {
				
			authentication = new UsernamePasswordAuthenticationToken(jwtService.getUsername(header), null, jwtService.getRoles(header));
	
		}
		
		// Manejamos el contexto de seguridad para que quede autenticado en la petición ya que no estamos manejando sesiones
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		// Continuamos con la ejecución del request tanto para los otros filtrs, para los servlet y controladores de Sping
		chain.doFilter(request, response);
		
	}
	
	// Método para validar si requiere autenticación
	protected boolean requeresAuthentication(String header) {
		
		// Usamos la constante que definimos de forma global en el JWTServiceImpl
		if (header == null || !header.startsWith(JWTServiceImpl.PREFIJO_TOKEN)) {
			return false;
		}
		
		return true;
		
	}

}
