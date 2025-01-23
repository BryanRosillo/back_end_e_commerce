package com.ecommerce.backend.excepciones;

/**
 * Excepción personalizada para manejar errores específicos relacionados con los pedidos.
 * Esta excepción puede ser lanzada cuando se produce un error durante el procesamiento de un pedido.
 */
public class ExcepcionPedido extends Exception{

	/**
	 * Constructor que crea una nueva instancia de {@code ExcepcionPedido} con un mensaje específico.
	 * 
	 * @param mensaje El mensaje de error que describe el problema que causó la excepción.
	 */	
	public ExcepcionPedido(String mensaje) {
		super(mensaje);
	}
	
}
