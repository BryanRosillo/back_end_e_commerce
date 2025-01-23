package com.ecommerce.backend.entidades;

import java.util.Date;

import lombok.Data;

/**
 * Representa un mensaje dentro del sistema de e-commerce.
 * Los mensajes pueden contener información sobre el contenido, el emisor y la fecha de creación.
 * Esta clase utiliza la anotación {@code @Data} de Lombok para generar automáticamente 
 * los métodos getter, setter, toString, equals y hashCode.
 */
@Data
public class Mensaje {
	
	/**
  * Contenido del mensaje.
  * Representa el texto o información que se desea enviar.
  */
	private String contenido;
	
	/**
	 * Emisor del mensaje.
	 * Indica el nombre o identificador del usuario que envió el mensaje.
	 */	
	private String emisor;
	
	/**
	 * Fecha de creación del mensaje.
	 * Por defecto, se inicializa con la fecha y hora actuales.
	 */
	private Date fecha = new Date();
	

}
