package com.bolsadeideas.springboot.horariointerceptor.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Autowired
	@Qualifier("horarioInterceptor")
	private HandlerInterceptor horario;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// Registramos el interceptor y excluimos la ruta cerrado para evitar que el interceptor se aplique a esta
		// y cause el loop ya que cuando va al index aplica el interceptor y valida y cuando llega a la ruta cerrado
		// se vuelve a validar el cual redirige nuevamente generando demsiadas redirecciones
		registry.addInterceptor(horario).excludePathPatterns("/cerrado");
	}

	
	
}
