package com.ecommerce.backend.configuraciones;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

	
	@Override
	public void addCorsMappings(CorsRegistry configuracion) {
		configuracion.addMapping("/**")
		.allowedOriginPatterns("*")
        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH");
	}
	
	
	
	
}
