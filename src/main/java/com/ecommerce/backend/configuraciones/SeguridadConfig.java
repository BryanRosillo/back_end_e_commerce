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

@Configuration
@EnableWebSecurity
public class SeguridadConfig{
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	
	@Bean FiltroAutenticacionJwt filtroAutenticacionJwt () {
		return new FiltroAutenticacionJwt();
	}
	
	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{
		return http.authorizeHttpRequests(peticion -> 
						peticion.requestMatchers("/login", "/registro", "/cambiar-contrasena", "/paypal/exito", "/paypal/cancelar").permitAll()
						.anyRequest().authenticated())
					.csrf(csrf -> 
						csrf.disable())
					.addFilterBefore(filtroAutenticacionJwt(), UsernamePasswordAuthenticationFilter.class)
					.build();
	}
	
	
	
}
