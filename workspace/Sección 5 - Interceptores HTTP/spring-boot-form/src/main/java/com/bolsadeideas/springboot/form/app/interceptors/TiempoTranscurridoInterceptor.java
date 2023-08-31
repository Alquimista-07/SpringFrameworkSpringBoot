package com.bolsadeideas.springboot.form.app.interceptors;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/*
 * NOTA: Los interceptores (interceptors) son objetos que son capaces de interponerse en las 
 *       llamadas a los métodos en los eventos de ciclo de vida de los beans de sesión y de 
 *       mensaje. Nos permiten encapsular conductas comunes a distintas partes de la aplicación 
 *       que normalmente no tienen que ver con la lógica de negocio.
 *       
 *       Los interceptores realizan tareas antes y después de la ejecución de un Action y también 
 *       pueden evitar que un Action se ejecute (por ejemplo si estamos haciendo alguna validación 
 *       que no se ha cumplido). Sirven para ejecutar algún proceso particular que se quiere aplicar 
 *       a un conjunto de Actions.
 */

@Component
public class TiempoTranscurridoInterceptor implements HandlerInterceptor {
	
	// Atributo log para poder registrar eventos en el log o traza de la aplicación
	private static final Logger logger = LoggerFactory.getLogger(TiempoTranscurridoInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// Con el logger podemos mostrar información del interceptor
		logger.info("TiempoTranscurridoInterceptor: preHandle() entrando...");
		
		// Calculamos el tiempo en ms el tiempo actual
		long tiempoInicio = System.currentTimeMillis();
		
		// Lo gardamos en el request
		request.setAttribute("tiempoInicio", tiempoInicio);
		
		// Simulamos una demora
		Random random = new Random();
		// Demora entre 0 y 499 ms
		Integer demora = random.nextInt(500);
		Thread.sleep(demora);
		
		// Si retorna true continua con la ejecución normal del controlador y demás interceptores
		// de lo contrario si es false termina la ejecución.
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		// Calculamos el tiempo en ms el tiempo actual
		long tiempoFin = System.currentTimeMillis();
		// Calculamos el tiempo en ms el tiempo actual
		
		// Obtenemos el tiempo inical 
		long tiempoInicio = (Long) request.getAttribute("tiempoInicio");
		
		long tiempoTranscurrido = tiempoFin - tiempoInicio;
		
		// Pasamos los datos a la vista
		if( modelAndView != null ) {
			modelAndView.addObject("tiempoTranscurrido", tiempoTranscurrido);
		}
		
		// Con el logger podemos mostrar información del interceptor
		logger.info("Tiempo Transcurrido: " + tiempoTranscurrido + " ms");
		
		// Con el logger podemos mostrar información del interceptor
		logger.info("TiempoTranscurridoInterceptor: postHandle() saliendo...");
		
	}
	
	

}
