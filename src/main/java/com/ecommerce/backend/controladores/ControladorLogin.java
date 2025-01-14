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

@RestController
@RequestMapping(path="/login", produces="application/json")
public class ControladorLogin {

	private final ServicioUsuario servicioUsuario;
	
	@Autowired
	public ControladorLogin(ServicioUsuario servicioUsuario) {
		this.servicioUsuario = servicioUsuario;
	}

	@PostMapping
	public ResponseEntity<Object> validarCuenta(@RequestBody Usuario usuario) {
		try{
			String token = this.servicioUsuario.validarUsuario(usuario.getUsername());
			return ResponseEntity.ok().body(token);
		}catch(Exception e){
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
	}
	

}
