package com.ecommerce.backend.excepciones;

/**
 * Excepción personalizada para manejar errores específicos relacionados con los usuarios.
 * Esta excepción puede ser lanzada cuando se produce un error durante el procesamiento de un usuario,
 * como problemas al crear, actualizar o eliminar un usuario en el sistema.
 */
public class ExcepcionUsuario extends Exception {

	/**
	 * Constructor que crea una nueva instancia de {@code ExcepcionUsuario} con un mensaje específico.
	 * 
	 * @param mensaje El mensaje de error que describe el problema que causó la excepción.
	 */	
	public ExcepcionUsuario(String mensaje) {
		super(mensaje);
	}
	
}
