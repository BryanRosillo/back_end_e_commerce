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

@Slf4j
@Component
public class FiltroAutenticacionJwt extends OncePerRequestFilter{
	
	@Autowired
	private ServicioJwt servicioJwt;
	
	@Autowired
	private ServicioUsuario servicioUsuario;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String cabezeraAutorizacion = request.getHeader("Authorization");
		
		if((cabezeraAutorizacion!=null) && (cabezeraAutorizacion.startsWith("Bearer "))) {
			String token = cabezeraAutorizacion.substring(7);
			if(this.servicioJwt.validarToken(token)) {
				Long id = this.servicioJwt.extraerId(token);
				try {
					
					String direccionIp = request.getHeader("X-Forwarded-For");
					if(direccionIp==null||direccionIp.isEmpty()) {
						direccionIp = request.getRemoteAddr();
					}
					
					Usuario usuario = this.servicioUsuario.buscarUsuarioPorId(id);
					UsernamePasswordAuthenticationToken autenticacion = new UsernamePasswordAuthenticationToken(usuario.getUsername(), null, null);
					autenticacion.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(autenticacion);
			
				} catch (Exception e) {
					log.error("Hubo un problema en la autorización: {}", e.getMessage());
					throw new ServletException("El token ingresado no es válido.");
				}
			}
		}
		
		filterChain.doFilter(request, response);
		
	}

}
