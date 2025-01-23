package com.ecommerce.backend.configuraciones;

import java.time.LocalDateTime;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.ecommerce.backend.excepciones.ExcepcionUsuario;
import com.ecommerce.backend.servicios.ServicioUsuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Interceptor para registrar detalles de las solicitudes HTTP realizadas.
 * Este interceptor captura la información de cada solicitud entrante, incluyendo la IP
 * del cliente, el nombre de usuario autenticado, la fecha y hora, el endpoint llamado
 * y el método HTTP utilizado. Esta información se registra en los logs del sistema.
 * 
 * Se utiliza para fines de auditoría y depuración.
 */
@Slf4j
@Component
public class LogConfig implements HandlerInterceptor {
	
	/**
	 * Método que se ejecuta antes de que se maneje la solicitud HTTP. Registra información
	 * relevante sobre la solicitud, como la IP del cliente, el usuario autenticado (si está disponible),
	 * la fecha y hora, el endpoint llamado y el método HTTP.
	 * 
	 * @param request La solicitud HTTP que se está procesando.
	 * @param response La respuesta HTTP que se va a enviar.
	 * @param handler El manejador que procesa la solicitud.
	 * @return {@code true} para permitir que la solicitud continúe hacia el controlador.
	 * @throws Exception Si ocurre algún error durante el procesamiento del interceptor.
	 */	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// Obtener la dirección IP del cliente desde la cabecera X-Forwarded-For o el remote address
		String direccionIp = request.getHeader("X-Forwarded-For");
		if(direccionIp==null||direccionIp.isEmpty()) {
			direccionIp = request.getRemoteAddr();
		}

		// Obtener el nombre de usuario autenticado, si está disponible
		String username = "";
		try {
			username = ServicioUsuario.devolverUsernameAutenticado();
		}catch(ExcepcionUsuario e) {
			username = "DESCONOCIDO"; 
		}
		
		// Obtener la fecha y hora actual en formato ISO
		String timestamp = LocalDateTime.now().toString();
		
		// Registrar los detalles de la solicitud en los logs
		log.info("PETICION REALIZADA. Dia y hora: {}, Usuario: {}, IP: {}, Endpoint llamado: {}, Metodo: {}", timestamp, username, direccionIp, request.getRequestURI(), request.getMethod());
		
		// Permitir que la solicitud continúe
		return true;
	}

}
