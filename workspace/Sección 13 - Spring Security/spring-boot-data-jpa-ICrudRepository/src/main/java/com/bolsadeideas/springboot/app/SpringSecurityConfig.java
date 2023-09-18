package com.bolsadeideas.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringSecurityConfig  {
	
	// Creamos un método que permite registrar el password encoder (En este caso BCrypt que actualmente es el más robusto)
	// por defecto en la configuración de Spring Security
	@Bean
	public static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Implementamos un método para poder registrar y configurar los usuarios de nuestro sistema de seguridad.
	// Por ejemplo el usuario administrador o cualquier otro, asignar un username, un password y sus roles.
	// Inicialmente se van a guardar estos usuarios en memoria pero luego se van a guardar en base de datos usando
	// con JDBC con JPA.
	@Autowired
	public void configurerGlobal( AuthenticationManagerBuilder builder ) throws Exception {
		
		// Usamos el password encoder
		PasswordEncoder encoder = passwordEncoder();
		
		// Creamos y encriptamos la contraseña de los usuarios.
		
		// NOTA: Cuando usamos el :: es una característica de Java 8 y automáticamente lo que hace es obtener el argumento de la 
		//       función lamda (flecha) encoder y pasarla al método encode y todo esto de forma implícita dejando el código más 
		//       elegante y simple. Y lo que estamos pasando es el password para ser encriptado
		UserBuilder users = User.builder().passwordEncoder( encoder :: encode );
		
		// Ahora que tenemos el UserBuilder configurado ahora si procedemos a crear los usuarios en memoria.
		builder.inMemoryAuthentication().
		withUser(users.username("admin").password("12345").roles("ADMIN", "USER")).
		withUser(users.username("andres").password("12345").roles("USER"));
		
	}
	
}
