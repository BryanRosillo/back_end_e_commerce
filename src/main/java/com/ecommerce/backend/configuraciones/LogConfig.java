package com.ecommerce.backend.configuraciones;

import java.time.LocalDateTime;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.ecommerce.backend.excepciones.ExcepcionUsuario;
import com.ecommerce.backend.servicios.ServicioUsuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LogConfig implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String direccionIp = request.getHeader("X-Forwarded-For");
		if(direccionIp==null||direccionIp.isEmpty()) {
			direccionIp = request.getRemoteAddr();
		}
		
		String username = "";
		try {
			username = ServicioUsuario.devolverUsernameAutenticado();
		}catch(ExcepcionUsuario e) {
			username = "DESCONOCIDO"; 
		}
		
		String timestamp = LocalDateTime.now().toString();
		
		log.info("PETICION REALIZADA. Dia y hora: {}, Usuario: {}, IP: {}, Endpoint llamado: {}, Metodo: {}", timestamp, username, direccionIp, request.getRequestURI(), request.getMethod());
		
		return true;
	}

}
