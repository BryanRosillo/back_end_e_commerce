package com.ecommerce.backend.excepciones;

import java.util.HashMap;
import java.util.Map;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import lombok.extern.slf4j.Slf4j;

/**
 * Clase para manejar globalmente las excepciones en la aplicación.
 * Utiliza {@link RestControllerAdvice} para interceptar las excepciones y devolver una respuesta adecuada al cliente.
 * Los métodos en esta clase se encargan de capturar y manejar excepciones específicas o generales,
 * como errores de validación y errores internos del servidor.
 */
@Slf4j
@RestControllerAdvice
public class ManejadorGlobalExepciones {
	
	private final String NOMBRE_VARIABLE_ERROR = "error";
	
	/**
	 * Maneja las excepciones de validación de entidades que ocurren cuando los datos de entrada no son válidos.
	 * Captura la excepción {@link MethodArgumentNotValidException} y devuelve un mensaje de error con el detalle de la validación fallida.
	 * 
	 * @param e La excepción capturada que contiene los detalles de la validación fallida.
	 * @return Una respuesta {@link ResponseEntity} con el mensaje de error y un código de estado {@link HttpStatus#BAD_REQUEST}.
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> manejarErroresValidacionEntidades(MethodArgumentNotValidException e){
		log.warn("Hubo un error en la validación de la entidad: {}", e.getMessage());
		String mensajeError = e.getBindingResult()
								.getFieldErrors()
								.stream()
								.findFirst()
								.map(DefaultMessageSourceResolvable::getDefaultMessage)
								.orElse("Error de validación de la entidad");

		Map<String, String> error = new HashMap<>();
		error.put(NOMBRE_VARIABLE_ERROR, mensajeError);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	/**
	 * Maneja excepciones generales que ocurren en el servidor.
	 * Captura excepciones específicas como {@link ExcepcionProducto}, {@link ExcepcionUsuario} y otras excepciones generales.
	 * Devuelve un mensaje genérico de error interno con un código de estado {@link HttpStatus#INTERNAL_SERVER_ERROR}.
	 * 
	 * @param e La excepción general que ocurrió en el servidor.
	 * @return Una respuesta {@link ResponseEntity} con un mensaje de error genérico y un código de estado {@link HttpStatus#INTERNAL_SERVER_ERROR}.
	 */	
	@ExceptionHandler({ExcepcionProducto.class, Exception.class, ExcepcionUsuario.class})
	public ResponseEntity<Object> manejarExcepcionesGenerales(Exception e){
		log.error("Hubo un problema en el servidor: {}", e.getMessage(), e);
		Map<String, String> error = new HashMap<>();
		error.put(NOMBRE_VARIABLE_ERROR, "Lo siento, hubo un problema. Informe al desarrollador.");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}

}
