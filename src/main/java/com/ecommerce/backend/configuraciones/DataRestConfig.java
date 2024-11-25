package com.ecommerce.backend.configuraciones;

import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.ecommerce.backend.entidades.Usuario;

@Component
public class DataRestConfig implements RepositoryRestConfigurer{

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
		cors.addMapping("/**")
		.allowedOriginPatterns("*")
		.allowedMethods("GET", "POST", "PUT", "DELETE")
		.allowedHeaders("*")
		.allowCredentials(false);
		
		config.exposeIdsFor(Usuario.class);
	}
	
	
	

}
