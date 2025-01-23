package com.ecommerce.backend.configuraciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	@Autowired
	private LogConfig logConfig;
	
	@Value("${front.dominio}")
	private String FRONT_DOMINIO;
	

	@Override
	public void addCorsMappings(CorsRegistry configuracion) {
		configuracion.addMapping("/**")
		.allowedOriginPatterns(this.FRONT_DOMINIO)
        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
        .allowedHeaders("*");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(logConfig).addPathPatterns("/**");
	}
	
}
