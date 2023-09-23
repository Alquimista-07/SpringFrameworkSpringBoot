package com.bolsadeideas.springboot.app.auth.filter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	// Creamos el Authentication manager que es el que se va a encargar de realizar la autenticación
	// usando el proveedor JpaUserDetailsService
	private AuthenticationManager authenticationManager;
	
	// Constructor
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login", "POST")); 
	}

	// NOTA: Este método sobreescrito es importante ya que se encarga de realizar la autenticación, o al menos de intentar realizar
	//       el login y por debajo este método es bastante importante ya que va a trabajar de la mano con nuestro proveedor de 
	//       autenticación. Por ejemplo en el package service, tenemos nuestra JpaUserDetailsService que es el proveedor de autenticación
	//       y realiza el login con JPA. Por lo tanto por detrás de escena este método va a llamar a un componente autentication manager que
	//       se encarga de trabajar de la mano con el proveedor JpaUserDetailsService
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		
		if (username == null) {
			username = "";
		}
		
		if (password == null) {
			password = "";
		}
		
		if (username != null && password != null) {
			logger.info("Username desde request parameter (form-data): " + username);
			logger.info("Password desde request parameter (form-data): " + password);
		}
		
		username = username.trim();
		
		// El siguiente paso es creat el username password authentication token que se encarga de contener las
		// credenciales.
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
		
		return authenticationManager.authenticate(authToken);
	}

	
	
}
