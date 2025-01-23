package com.ecommerce.backend.configuraciones;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.ecommerce.backend.entidades.Usuario;
import com.ecommerce.backend.servicios.ServicioJwt;
import com.ecommerce.backend.servicios.ServicioUsuario;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Filtro de autenticación JWT para verificar y validar el token de acceso
 * enviado en las solicitudes HTTP.
 * 
 * Este filtro intercepta las solicitudes HTTP para verificar si contienen un token
 * de autenticación en la cabecera "Authorization". Si el token es válido, extrae
 * el ID del usuario, valida su autenticidad y lo configura en el contexto de seguridad
 * de Spring.
 */
@Slf4j
@Component
public class FiltroAutenticacionJwt extends OncePerRequestFilter{
	
	@Autowired
	private ServicioJwt servicioJwt;
	
	@Autowired
	private ServicioUsuario servicioUsuario;
	
	/**
	 * Método que se ejecuta para filtrar las solicitudes HTTP. Verifica si la solicitud
	 * contiene un token JWT válido en la cabecera "Authorization". Si es válido, extrae
	 * el ID del usuario y establece la autenticación en el contexto de seguridad.
	 * 
	 * @param request La solicitud HTTP que se está procesando.
	 * @param response La respuesta HTTP que se va a enviar.
	 * @param filterChain La cadena de filtros que sigue la solicitud.
	 * @throws ServletException Si ocurre un error en la autenticación del token.
	 * @throws IOException Si ocurre un error al procesar la solicitud.
	 */	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		// Obtener el token de la cabecera Authorization
		String cabezeraAutorizacion = request.getHeader("Authorization");
		
		// Verificar si el token existe y comienza con "Bearer "
		if((cabezeraAutorizacion!=null) && (cabezeraAutorizacion.startsWith("Bearer "))) {
			// Extraer el token sin la palabra "Bearer "
			String token = cabezeraAutorizacion.substring(7);
			
			// Validar el token
			if(this.servicioJwt.validarToken(token)) {
				Long id = this.servicioJwt.extraerId(token); // Extraer el ID del usuario del token
				try {
					// Buscar al usuario por su ID
					Usuario usuario = this.servicioUsuario.buscarUsuarioPorId(id);
					// Crear un token de autenticación para el usuario
					UsernamePasswordAuthenticationToken autenticacion = new UsernamePasswordAuthenticationToken(usuario.getUsername(), null, null);
					autenticacion.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					// Establecer la autenticación en el contexto de seguridad
					SecurityContextHolder.getContext().setAuthentication(autenticacion);
			
				} catch (Exception e) {
					// Si ocurre un error al encontrar el usuario, lanzar una excepción
					log.error("Hubo un problema en la autorización: {}", e.getMessage());
					throw new ServletException("El token ingresado no es válido.");
				}
			}
		}
		// Continuar con el siguiente filtro en la cadena
		filterChain.doFilter(request, response);
	}
}
