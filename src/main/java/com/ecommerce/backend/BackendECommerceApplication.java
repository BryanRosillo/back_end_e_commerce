package com.ecommerce.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación que inicia el servidor Spring Boot.
 * 
 * Esta clase contiene el punto de entrada para la ejecución de la aplicación,
 * donde se inicializa y arranca la aplicación de comercio electrónico.
 */
@SpringBootApplication
public class BackendECommerceApplication {

	/**
	 * Método principal que ejecuta la aplicación Spring Boot.
	 * 
	 * @param args Los argumentos de línea de comando proporcionados al ejecutar la aplicación.
	 */	
	public static void main(String[] args) {
		SpringApplication.run(BackendECommerceApplication.class, args);
	}
	

}
