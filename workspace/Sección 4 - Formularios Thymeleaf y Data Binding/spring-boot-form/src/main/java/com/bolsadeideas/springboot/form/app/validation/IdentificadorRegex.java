package com.bolsadeideas.springboot.form.app.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/*
 * NOTA: Esta clase la creamos como una anotación, y en el apartado marcamos
 *       runtime, field y method que es lo qque vamos a validar
 */

// NOTA: Enlazamos nustra clase de validación IdentificadorRegexValidador con la anotación a través
//       de la anotación @Constraint
@Constraint(validatedBy = IdentificadorRegexValidador.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface IdentificadorRegex {
	
	// NOTA: Agregamos la configuración por defecto y definimos el mensaje
	String message() default "Identificador Inválido";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

}
