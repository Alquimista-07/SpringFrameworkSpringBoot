package com.bolsadeideas.springboot.horariointerceptor.app.interceptors;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class HorarioInterceptor implements HandlerInterceptor {
	
	// Creamos atributos que permiten traer la información definida en el application properties
	@Value("${config.horario.apertura}")
	private Integer apertura;
	
	@Value("${config.horario.cierre}")
	private Integer cierre;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// Obtenemos la hora actual
		Calendar calendar = Calendar.getInstance();
		int hora = calendar.get(Calendar.HOUR_OF_DAY);
		
		if( hora >= apertura && hora < cierre ) {
			
			// El StringBuilder es un objeto de java que permite crear strings que sean mutables,
			// es decir que podemos irle cambiando la instancia, ir concatenando, agregando sin
			// tener que crear nuevos objetos.
			StringBuilder mensaje = new StringBuilder("Bienvenido al horario de atención a clientes");
			mensaje.append(", atendemos desde las ");
			mensaje.append(apertura);
			mensaje.append("hrs. ");
			mensaje.append("hasta las ");
			mensaje.append(cierre);
			mensaje.append("hrs. ");
			mensaje.append("Gracias por su visita.");
			
			// Pasamos el mensaje a los atributos de request, para pasar parámetros desde el interceptor a la vista
			request.setAttribute("mensaje", mensaje.toString());
			return true;
		}
		
		// En caso de que no sea válido debido a que esta fuera de horario manejamos el return false y redireccionamos
		response.sendRedirect(request.getContextPath().concat("/cerrado"));
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		// Obtenemos el mensaje
		String mensaje  = (String) request.getAttribute("mensaje");
		
		// y se lo pasamos a la vista
		modelAndView.addObject("horario", mensaje);
	}
	
	

}
