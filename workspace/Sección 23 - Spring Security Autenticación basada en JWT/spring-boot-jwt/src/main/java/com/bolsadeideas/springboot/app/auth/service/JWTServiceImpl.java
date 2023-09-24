package com.bolsadeideas.springboot.app.auth.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.bolsadeideas.springboot.app.auth.SimpleGrantedAuthorityMixin;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTServiceImpl implements JWTService {
	
	public static final SecretKey SECRET_KEY = new SecretKeySpec("Alguna clave Secreta 1234567890".getBytes(), SignatureAlgorithm.HS512.getJcaName());

	@Override
	public String create(Authentication auth) throws IOException {
		
		String username = ((User) auth.getPrincipal()).getUsername();
		
		// Obtenemos los roles ya que no tenemos un méotodo propio para pasarlos al token por lo tanto los obtenemos y los pasamos en los Claims
		// y hay que tener en cuenta que roles es un objeto por lo tanto tenemos que pasar un string en formato JSON
		// por lo tanto lo convertimos a JSON con ayuda del ObjectMapper()
		Collection <? extends GrantedAuthority> roles = auth.getAuthorities();
		
		Claims claims = Jwts.claims();
		claims.put("authorities", new ObjectMapper().writeValueAsString(roles));
		
		
		String token = Jwts.builder()
				.setClaims(claims)
				.setSubject( username )
				.signWith(SignatureAlgorithm.HS512, "Alguna clave Secreta 1234567890".getBytes())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 14000000L)) // El valor 3600000 equivale a 1 hora por lo tanto podemos multiplicarlo para asingar el tiempo que necesitemos. En este caso del ejemplo multiplicamos por 4 para asingar 4 horas al token 
				.compact();
		
		return token;
	}

	@Override
	public boolean validate(String token) {
		
		try {
			
			getClaims(token);
			
			return true;
		
		} catch (JwtException | IllegalArgumentException e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public Claims getClaims(String token) {
		
		// Implementamos la validación del token y para ello tenemos que invocar el método parse para analizar el token
		// a través de la misma clase del toen que es Jwts
		Claims claims = Jwts.parser()
				// Y pasamos la llave que es la misma que pasamos en el JWTAuthenticationFilter, pero luego se va a crear de forma global
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(resolve(token)).getBody();
		
		return claims;
	}

	@Override
	public String getUsername(String token) {
		return getClaims(token).getSubject();
	}

	@Override
	public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException {
		
		Object roles = getClaims(token).get("authorities");
		
		Collection<? extends GrantedAuthority> authorities = Arrays.asList( new ObjectMapper()
				.addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
				.readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class) );
		
		return authorities;
	}

	@Override
	public String resolve(String token) {
		
		// Validamos si viene el token y adicionalmente si viene con el prefijo Bearer
		if (token != null && token.startsWith("Bearer ")) {			
			// Lo siguiente es analizar, es validar, pero como sabemos viene con el prefijo Bearer el cual tenemos que quitar
			return token.replace("Bearer ", "");
		}
		
		return null;
		
	}

}
