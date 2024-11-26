package com.ecommerce.backend.configuraciones;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SeguridadConfig {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{
		return http.authorizeHttpRequests(peticion -> 
						peticion.requestMatchers("/h2-console/**").permitAll()
						.anyRequest().permitAll())
					.csrf(csrf -> 
						csrf.disable())
					.headers(headers -> 
						headers.frameOptions(origin -> 
							origin.sameOrigin()))
					.build();
	}
}
