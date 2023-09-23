package com.bolsadeideas.springboot.app.auth.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.bolsadeideas.springboot.app.models.entity.Usuario;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
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
		
		// NOTA: Como ahora estamos recibiendo por raw estos dos if los tenemos que comentar
		/*
		if (username == null) {
			username = "";
		}
		
		if (password == null) {
			password = "";
		}
		*/
		
		if (username != null && password != null) {
			logger.info("Username desde request parameter (form-data): " + username);
			logger.info("Password desde request parameter (form-data): " + password);
		} else {
			// Ahora como vamos a enviar en usuario y la contraseña en raw en bruto con formato JSON y no en el form-data
			// tenemos que manejarlo y convertir el JSON a un objeto y recibirlo de la siguiente manera:
			Usuario user = null;
			
			try {
				user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
				
				username = user.getUsername();
				password = user.getPassword();
				
				logger.info("Username desde request InputStream (raw): " + username);
				logger.info("Password desde request InputStream (raw): " + password);
			
			} catch (StreamReadException e) {
				e.printStackTrace();
			} catch (DatabindException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		username = username.trim();
		
		// El siguiente paso es creat el username password authentication token que se encarga de contener las
		// credenciales.
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
		
		return authenticationManager.authenticate(authToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		String username = ((User) authResult.getPrincipal()).getUsername();
		
		// Obtenemos los roles ya que no tenemos un méotodo propio para pasarlos al token por lo tanto los obtenemos y los pasamos en los Claims
		// y hay que tener en cuenta que roles es un objeto por lo tanto tenemos que pasar un string en formato JSON
		// por lo tanto lo convertimos a JSON con ayuda del ObjectMapper()
		Collection <? extends GrantedAuthority> roles = authResult.getAuthorities();
		
		Claims claims = Jwts.claims();
		claims.put("authorities", new ObjectMapper().writeValueAsString(roles));
		
		
		String token = Jwts.builder()
				.setClaims(claims)
				.setSubject( username )
				.signWith(SignatureAlgorithm.HS512, "Alguna clave Secreta 1234567890".getBytes())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 14000000L)) // El valor 3600000 equivale a 1 hora por lo tanto podemps multiplicarlo para asingar el tiempo que necesitemos. En este caso del ejemplo multiplicamos por 4 para asingar 4 horas al token 
				.compact();
		
		// Pasamos el token en la cabecera de la respuesta.
		// OJO es importante que lo pasemos como Authorization
		// y Bearer, es decir tal cual esta escrito el código.
		response.addHeader("Authorization", "Bearer " + token);
		
		// Pasamos en forma JSON el principal, el token y un mensaje
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("user", (User) authResult.getPrincipal());
		body.put("token", token);
		// NOTA: El patrón %s permite reemplazar por la data que tiene el segundo parántro del format, en este caso el nombre del usuario
		body.put( "mensaje", String.format("Hola %s, has iniciado sesión con éxito", username) );
		
		// Para pasar los datos a la respuesta tenemos que convertir a JSON unsando el método ObjectMapper
		// como se muestra a continuación
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		
		// Indicamos el estado de la petición y el content type
		response.setStatus(200);
		response.setContentType("application/json");
		
	}

	
	
}
