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

@Slf4j
@RestControllerAdvice
public class ManejadorGlobalExepciones {
	
	private final String NOMBRE_VARIABLE_ERROR = "error";
	
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

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> manejarExcepcionesGenerales(Exception e){
		log.error("Hubo un problema en el servidor: {}", e.getMessage(), e);
		Map<String, String> error = new HashMap<>();
		error.put(NOMBRE_VARIABLE_ERROR, "Lo siento, hubo un problema. Informe al desarrollador.");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}

}
