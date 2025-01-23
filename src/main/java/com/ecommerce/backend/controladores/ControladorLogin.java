package com.ecommerce.backend.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ecommerce.backend.entidades.Usuario;
import com.ecommerce.backend.servicios.ServicioUsuario;

/**
 * Controlador encargado de gestionar el inicio de sesión de los usuarios.
 * Este controlador valida las credenciales de un usuario y devuelve un token de acceso
 * si las credenciales son correctas.
 */
@RestController
@RequestMapping(path="/login", produces="application/json")
public class ControladorLogin {

	private final ServicioUsuario servicioUsuario;
	
	/**
	 * Constructor que inyecta el servicio de usuario.
	 * 
	 * @param servicioUsuario El servicio que maneja la lógica de validación del usuario.
	 */	
	@Autowired
	public ControladorLogin(ServicioUsuario servicioUsuario) {
		this.servicioUsuario = servicioUsuario;
	}

	/**
	 * Método que recibe las credenciales de un usuario en el cuerpo de la solicitud
	 * y valida si el usuario existe. Si las credenciales son correctas, se genera
	 * y devuelve un token de acceso.
	 * 
	 * @param usuario El objeto Usuario que contiene las credenciales (nombre de usuario y contraseña).
	 * @return Un {@link ResponseEntity} con el token de acceso si las credenciales son válidas,
	 *         o un estado HTTP 401 (Unauthorized) si las credenciales no son correctas.
	 */	
	@PostMapping
	public ResponseEntity<Object> validarCuenta(@RequestBody Usuario usuario) {
		try{
			String token = this.servicioUsuario.validarUsuario(usuario.getUsername(), usuario.getPassword());
			return ResponseEntity.ok().body(token);
		}catch(Exception e){
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
	}
	

}
