package com.bolsadeideas.springboot.web.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/*
 * NOTA: Para esto creamos un archivo nuevo de properties ya que generalmente
 *       el archivo application.properties se usa para configuración de sistema 
 *       ,variables de entorno, bases de datos, etc.
 *       Adicionelnte de esta forma podemos tener varios archivos properties
 *       para cada cosa separados por coma ya que es un arreglo de archivos
 *       lo que indicamos en el @PropertySources
 */

@Configuration
@PropertySources({
	@PropertySource("classpath:textos.properties")
})
public class TextosPropertiesConfig {

}
