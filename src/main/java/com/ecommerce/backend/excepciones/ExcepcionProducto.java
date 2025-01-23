package com.ecommerce.backend.excepciones;

/**
 * Excepción personalizada para manejar errores específicos relacionados con los productos.
 * Esta excepción puede ser lanzada cuando se produce un error durante el procesamiento de un producto,
 * como al intentar crear, actualizar o eliminar un producto en el sistema.
 */
public class ExcepcionProducto extends Exception {

	/**
	 * Constructor que crea una nueva instancia de {@code ExcepcionProducto} con un mensaje específico.
	 * 
	 * @param mensaje El mensaje de error que describe el problema que causó la excepción.
	 */	
	public ExcepcionProducto(String mensaje) {
		super(mensaje);
	}
	
}
