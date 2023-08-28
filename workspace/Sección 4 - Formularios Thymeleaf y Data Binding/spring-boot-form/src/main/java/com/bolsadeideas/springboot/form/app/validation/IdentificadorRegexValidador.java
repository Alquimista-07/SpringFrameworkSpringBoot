package com.bolsadeideas.springboot.form.app.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/*
 * NOTA: Esta clase recibe una interace la cual implementa la interface ConstraintValidator que recibe el tipo 
 *       que en este caso es la clase anotación creada y el tipo del campo que en este caso como es el identificador
 *       y este es String eso es lo que vamos a indicar
 */
public class IdentificadorRegexValidador implements ConstraintValidator<IdentificadorRegex, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// NOTA: Nos traemos la validación que habíamos creado en la clase Usuario Validador para la expresión regular
		//       y la ajustamo ya que vamos a hacerlo a través de una anotiación personalizada.
		if (value.matches("[0-9]{2}[.][\\d]{3}[.][\\d]{3}[-][A-Z]{1}")) {
			return true;
		}

		return false;
	}

}
