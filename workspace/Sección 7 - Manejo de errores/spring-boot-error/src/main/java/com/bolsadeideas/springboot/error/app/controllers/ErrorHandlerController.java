package com.bolsadeideas.springboot.error.app.controllers;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bolsadeideas.springboot.error.app.errors.UsuarioNoEncontradoException;

// NOTA: Esta clase va a ser un controlador personalizado que nos va a permitir manejar errores como por
//       ejemplo el ArithmeticException que se genera al dividir por cero. Con la diferencia es que este
//       controlador no se anota como lo haríamos normalmente con el @Controller sino que se anota con el 
//       @ControllerAdvice y es bastante importante anotar de esta forma ya que esta anotación indica que es 
//       para manejar errores y excepciones capturandolas para manejarlas. Otra diferencia es que acá no se
//       mapea a una ruta como se hace con un controlador normal sino que se mapea a una excepción y se retorna
//       a una vista.
@ControllerAdvice
public class ErrorHandlerController {
	
	// OJO NOTA: En este caso estamos creando una plantilla html para cada excepción, pero esto no sería necesario
	//           ya que si nos damos cuenta básicamente el contendido que le estamos pasando con el model es el mismo
	//           por lo tanto podríamos crear una plantilla genérica y apuntar en el return de cada método a esa plantilla
	//           html en la vista.

	// NOTA: Como se mencionó anteriormente acá no se mapea a una ruta sino a una excepción
	//       por lo tanto anotamos con @ExceptionHandler() y le pasamos como argumento la
	//       excepción, que en este caso la división por cero si nos fijamos en el trace 
	//       que tenemos muestra que es un ArithmeticException. Adicionalmente podemos pasar
	//       más de una excepción indicandola como un arreglo
	@ExceptionHandler(ArithmeticException.class)
	public String aritmeticaError (ArithmeticException ex, Model model) {
		model.addAttribute("error", "Error de aritmética");
		model.addAttribute("message", ex.getMessage());
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		model.addAttribute("timestamp", new Date());
		return "error/aritmetica";
	}
	
	// Creamos el método para capturar la excepción de NumberFormat Exception
	@ExceptionHandler(NumberFormatException.class)
	public String numeroFormatoError(NumberFormatException ex, Model model) {
		model.addAttribute("error", "Error: Fomato de número incorrecto!");
		model.addAttribute("message", ex.getMessage());
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		model.addAttribute("timestamp", new Date());
		return "error/numero-formato";
	}
	
	// Creamos el método para capturar la excepción del usuario no encontrado
		@ExceptionHandler(UsuarioNoEncontradoException.class)
		public String usuarioNoEncontrado(UsuarioNoEncontradoException ex, Model model) {
			model.addAttribute("error", "Error: Usuario no encontrado!");
			model.addAttribute("message", ex.getMessage());
			model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
			model.addAttribute("timestamp", new Date());
			return "error/usuario";
		}
	
}
