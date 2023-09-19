package com.bolsadeideas.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;

import com.bolsadeideas.springboot.app.auth.handler.LoginSuccessHandler;

@Configuration
// Necesitamos habilitar las anotaciones @Secured("") que colocamos en la clase ClienteController y que ayudan validar
// la seguiridad en las rutas de una forma más sencilla y no como teníamos anteriormente con el método SecurityFilterChain
// al cual le pasamos la ruta ver, listar, uploads, etc que comentamos y agregar esto es bastante importante
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SpringSecurityConfig  {
	
	// Inyectamos
	@Autowired
	private LoginSuccessHandler successHandler;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	// Implementamos un método para poder registrar y configurar los usuarios de nuestro sistema de seguridad.
	// Por ejemplo el usuario administrador o cualquier otro, asignar un username, un password y sus roles.
	// Inicialmente se van a guardar estos usuarios en memoria pero luego se van a guardar en base de datos usando
	// con JDBC con JPA.
	@Autowired
	public void configurerGlobal( AuthenticationManagerBuilder builder ) throws Exception {
		
		// Usamos el password encoder
		PasswordEncoder encoder = this.passwordEncoder;
		
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
	
	// Arreglo de rutas permitidas a todo tipo de usuario
	public static final String[] ENDPOINTS_WHITELIST = {
			"/", 
			"/css/**", 
			"/js/**", 
			"/images/**", 
			"/listar"
		};
	
	// Configuración para la protección de rutas http
	@Bean
	public SecurityFilterChain filterChain( HttpSecurity http ) throws Exception {
		
		http.authorizeHttpRequests( request -> {
			
			request
				// Rutas publicas y de acceso a todo el mundo tanto para usuarios logueados como los anónimos
				.requestMatchers(ENDPOINTS_WHITELIST).permitAll()
				
				// Rutas privadas y que se quieren proteger dependiendo el role
				
				//NOTA: Acá comentamos el acceso a los recursos ver, uploads, form, eliminar y factura para mostrar como aplicar esto mismo de la seguriad y protección
				//      de recursos usando anotaciones de spring boot
				/*.requestMatchers("/ver/**").hasAnyRole("USER")*/
				/*.requestMatchers("/uploads/**").hasAnyRole("USER")*/
				/*.requestMatchers("/form/**").hasAnyRole("ADMIN")*/
				/*.requestMatchers("/eliminar/**").hasAnyRole("ADMIN")*/
				/*.requestMatchers("/factura/**").hasAnyRole("ADMIN")*/
				
				.anyRequest().authenticated();
				
				// Añadimos el formulario de login;
				
		}).formLogin(form -> form.loginPage("/login")
				.successHandler(successHandler)
				.permitAll())
		  .logout(logout -> logout.addLogoutHandler(new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive.COOKIES))))
		  .exceptionHandling(ex -> ex.accessDeniedPage("/error_403"));
		
		return http.build();
		
	}
	
}
