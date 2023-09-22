package com.bolsadeideas.springboot.app;

import java.nio.file.Paths;
import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

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
	
	// Para el tema del multilenguaje es necesario implementar unos beans, por ejemplo el local resolver
	// que se encarga del adaptador en el cual se almacena el locale, por ejemplo ya sea guardarlo en la
	// sessión http, en una cookie, etc. Adicionalmente es necesario tener un interceptor que se encargue 
	// de cambiar o modificar el locale.
	@Bean
	public LocaleResolver localeResolver() {
		
		// Creamos la implementación
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		
		// Asigamos el locale por defecto, y se le pasa por ejemplo la sigla del lenguaje "es" (Español) y del país "ES" (España)
		localeResolver.setDefaultLocale(new Locale("es", "ES"));
		
		return localeResolver;
	}
	
	// Creamos el interceptor para el local
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		
		// Creamos una instacia
		LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
		
		// Le asignamos el nombre del parámetro en este caso lang y básicamente indica que cada vez que se pase por url
		// a través de GET por ejemplo con el "es" y "ES" se va a ejecutar este interceptor y va a hacer el cambio del 
		// lenguaje del sitio web
		localeInterceptor.setParamName("lang");
		
		return localeInterceptor;
		
	}

	// Lo siguiente es registrar el interceptor a través del método sobreescrito
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}
	
	// Método que se encarga del serializar un objeto en un XML
	@Bean
	public Jaxb2Marshaller jaxb2Marshaller() {
		
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		
		marshaller.setClassesToBeBound(new Class[] {
			// Y dentro de este arreglo indicamos la clase wrapper pasandola con su package
			com.bolsadeideas.springboot.app.view.xml.ClienteList.class
		});
		
		return marshaller;
	}
	
	
	
}
