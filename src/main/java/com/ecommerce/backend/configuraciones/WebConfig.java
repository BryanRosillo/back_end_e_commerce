package com.ecommerce.backend.configuraciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración de la aplicación web.
 * Esta clase configura aspectos relacionados con CORS (Cross-Origin Resource Sharing)
 * y define los interceptores utilizados en las solicitudes HTTP.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	@Autowired
	private LogConfig logConfig;
	
	@Value("${front.dominio}")
	private String FRONT_DOMINIO;
	
	/**
	 * Configura las políticas de CORS para la aplicación.
	 * Permite las solicitudes desde el dominio especificado en la propiedad `front.dominio` 
	 * y habilita los métodos y cabeceras necesarios para las operaciones de la API.
	 * 
	 * @param configuracion La configuración CORS para las solicitudes HTTP.
	 */
	@Override
	public void addCorsMappings(CorsRegistry configuracion) {
		configuracion.addMapping("/**")
		.allowedOriginPatterns(this.FRONT_DOMINIO)
        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
        .allowedHeaders("*");
	}

	/**
	 * Configura los interceptores para la aplicación.
	 * En este caso, se añade un interceptor de logs para registrar información sobre
	 * las solicitudes HTTP realizadas a los endpoints de la aplicación.
	 * 
	 * @param registry El registro de interceptores para la configuración de los filtros.
	 */	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(logConfig).addPathPatterns("/**");
	}
	
}
