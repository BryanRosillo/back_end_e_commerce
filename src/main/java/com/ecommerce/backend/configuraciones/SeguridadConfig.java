package com.ecommerce.backend.configuraciones;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuración de seguridad para la aplicación.
 * Esta clase configura los aspectos relacionados con la autenticación, autorización y seguridad en la web
 * utilizando Spring Security, incluyendo la protección CSRF, control de cabeceras y la configuración de filtros.
 */
@Configuration
@EnableWebSecurity
public class SeguridadConfig{
	
	/**
	 * Bean que define el codificador de contraseñas utilizando el algoritmo BCrypt.
	 * Este codificador es utilizado para almacenar las contraseñas de manera segura.
	 * 
	 * @return El codificador de contraseñas basado en BCrypt.
	 */	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * Bean que configura el gestor de autenticación.
	 * El gestor de autenticación es responsable de autenticar a los usuarios.
	 * 
	 * @param authConfig Configuración de autenticación.
	 * @return El gestor de autenticación.
	 * @throws Exception Si ocurre un error al obtener el gestor de autenticación.
	 */	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	
	/**
	 * Bean que configura el filtro de autenticación JWT.
	 * Este filtro es responsable de validar los tokens JWT en las solicitudes entrantes.
	 * 
	 * @return El filtro de autenticación JWT.
	 */	
	@Bean FiltroAutenticacionJwt filtroAutenticacionJwt () {
		return new FiltroAutenticacionJwt();
	}
	
	/**
	 * Configura las reglas de seguridad para las solicitudes HTTP.
	 * Define qué endpoints son accesibles sin autenticación, deshabilita la protección CSRF, configura
	 * las cabeceras de seguridad y añade el filtro de autenticación JWT antes del filtro de autenticación estándar.
	 * 
	 * @param http La configuración de seguridad para las solicitudes HTTP.
	 * @return La configuración completa del filtro de seguridad.
	 * @throws Exception Si ocurre un error al configurar la seguridad.
	 */	
	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{
		return http.authorizeHttpRequests(peticion -> 
						peticion.requestMatchers("/login", "/registro", "/cambiar-contrasena", "/paypal/exito", "/paypal/cancelar").permitAll()
						.anyRequest().authenticated())
					.csrf(csrf -> 
						csrf.disable())
					.headers(cabecera->
							cabecera.frameOptions(opcion->opcion.deny())
									.httpStrictTransportSecurity(opcion->opcion.includeSubDomains(true).maxAgeInSeconds(31536000))
									.cacheControl(opcion->opcion.disable()))
					.addFilterBefore(filtroAutenticacionJwt(), UsernamePasswordAuthenticationFilter.class)
					.build();
	}
	
	
	
}
