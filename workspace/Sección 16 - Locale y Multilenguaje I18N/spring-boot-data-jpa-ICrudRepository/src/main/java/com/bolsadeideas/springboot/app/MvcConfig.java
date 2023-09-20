package com.bolsadeideas.springboot.app;

import java.nio.file.Paths;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	// NOTA: COMENTAMOS TODO EL MÉTODO PARA USAR UNA CUARTA FORMA DE SUBIR IMÁGENES
	/*@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.addResourceHandlers(registry);
		
		// Cambiamos para que sea absoluto y funcione con la tercera forma de pasar la ruta que definimos en el
		// controlador
		String resourcePath = Paths.get("uploads").toAbsolutePath().toUri().toString();
	
		// ----------------------------------------------------------------------------------------------------------------------------
		// COMENTAMOS YA QUE ESTA ES COMO FUNCIONA LA SEGUNDA FORMA DE PASAR LA RUTA PARA GUARDAR ARCHIVOS EN EL CONTROLADOR
		// ----------------------------------------------------------------------------------------------------------------------------
		// Registramos nuestra nueva ruta como un recurso estático.
		// Al cual le pasamos la ruta url y que definimos en la vista 
		// ver.html, a continuación tenemos que colocar el directorio .
		// Esto es para windows, pero por ejemplo en linux pasariamos 
		// la ubicación como "file:/opt/uploads/"
		// físico.
		//registry.addResourceHandler("/uploads/**")
		//.addResourceLocations("file:/C:/Temp/uploads/");
		// ----------------------------------------------------------------------------------------------------------------------------
		
		registry.addResourceHandler("/uploads/**")
		.addResourceLocations(resourcePath);
	}
	*/
	
	// Usamos este método para registrar un controlador de vista parametrizable y de esta forma personalizar
	// la pantalla cuando el usuario no tiene acceso a una ruta.
	// NOTA: Este método tiene que llamarese addViewControllers oblitagoriamente 
	public void addViewControllers(ViewControllerRegistry registry) {
		// Recibe como primer parámetro la ruta y como segundo parámetro la vista html
		registry.addViewController("/error_403").setViewName("error_403");
	}
	
	// Creamos un método que permite registrar el password encoder (En este caso BCrypt que actualmente es el más robusto)
		// por defecto en la configuración de Spring Security
	@Bean
	public static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}	

}
