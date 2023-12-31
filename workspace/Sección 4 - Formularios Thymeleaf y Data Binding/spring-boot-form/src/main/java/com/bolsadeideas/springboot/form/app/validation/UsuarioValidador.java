package com.bolsadeideas.springboot.form.app.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bolsadeideas.springboot.form.app.models.domain.Usuario;

@Component
public class UsuarioValidador implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// Acá tenemos que dar soporte a nuestro Entity o clase que queremos validar
		return Usuario.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// NOTA: Comentamos usuario ya que no lo vamos a usar debido a que creamos nuestra propia anotación
		//       de validación para la expresión regular
		// Casteamos el target como Usuario
		// Usuario usuario = (Usuario) target;
		
		// Validamos que el nombre no sea vacío usando el helper de Spring
		// Acá pasamos como segundo argumento un string pero con el mismo nombre que tenemos el 
		// atributo en el POJO, y como tercer argumento pasamos la llave del propertie tal cual
		// como se llama en nuestro archivo messages.properties
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombre", "NotEmpty.usuario.nombre");
		
		/*
		 *  Otra forma de hacer la misma validación del nombre sería:
		 *			if(usuario.getNombre().isEmpty()) {
		 *				errors.rejectValue("nombre", "NotEmpty.usuario.nombre");
		 *			}
		 *  
		 *  
		 */
		
		/*
		 * NOTA: Comentamos esta validación de nuestra clase validador ya que 
		 *       creamos nuestra propia anotiación y por lo tanto esta anotación
		 *       se va a encargar de esto.
		 *       
		 *       // Validamos el idRegex
	     *       if(!usuario.getIdRegex().matches("[0-9]{2}[.][\\d]{3}[.][\\d]{3}[-][A-Z]{1}")) {
		 *      	errors.rejectValue("idRegex", "pattern.usuario.idRegex");
		 *       }
		 */
	}

}
