package com.bolsadeideas.springboot.form.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// NOTA: Para esta clase es necesario implmentar la interface y registrae con el decorador
//       @Configuration
@Configuration
public class MvcConfig implements WebMvcConfigurer {
	
	// Para poder registrar el interceptor como esta anotado con component, es decir ya esta
	// registrado en el contenedor de spirng simplemente lo inyectamos. Y para evitar confuciones
	// ya que puede existir más de un interceptor, entonces le colocamos un nombre al componente
	// (tiempoTranscurridoInterceptor)
	@Autowired
	@Qualifier("tiempoTranscurridoInterceptor")
	private HandlerInterceptor tiempoTranscurridoInterceptor;

	// Registramos el inteceptor
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// Si no agregamos el patron de ruta (addPathPatterns()) el intetceptor se va a aplicar de forma global
		// a todas las ruta, por lo tanto si queremos que sean algunas en específico debemos indicar dicho método
		// addPathPatterns y para el cual se pasa una lista de string sepsrada por coma (Ej: .addPathPatterns("/form/**", "/admin/usuarios");)
		registry.addInterceptor(tiempoTranscurridoInterceptor).addPathPatterns("/form/**");
	}
	
	

}
