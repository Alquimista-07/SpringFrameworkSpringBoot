package com.bolsadeideas.springboot.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.addResourceHandlers(registry);
	
		// Registramos nuestra nueva ruta como un recurso estático.
		// Al cual le pasamos la ruta url y que definimos en la vista 
		// ver.html, a continuación tenemos que colocar el directorio .
		// Esto es para windows, pero por ejemplo en linux pasariamos 
		// la ubicación como "file:/opt/uploads/"
		// físico.
		registry.addResourceHandler("/uploads/**")
		.addResourceLocations("file:/C:/Temp/uploads/");
	}
	
	

}
