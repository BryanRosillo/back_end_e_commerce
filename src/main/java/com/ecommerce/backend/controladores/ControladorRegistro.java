package com.ecommerce.backend.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ecommerce.backend.entidades.Usuario;
import com.ecommerce.backend.entidades.UsuarioDTO;
import com.ecommerce.backend.servicios.ServicioUsuario;

import jakarta.validation.Valid;

@RestController
@RequestMapping(produces="application/json")
public class ControladorRegistro {
	
    private final ServicioUsuario servicioUsuario;

	@Autowired
	public ControladorRegistro(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }

    @PostMapping(path="/registro", consumes="application/json")
	public ResponseEntity<Usuario> registrarUsuario(@RequestBody @Valid Usuario usuario) {
        try{
            this.servicioUsuario.registrarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.OK).body(usuario);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
	}
	
    @PatchMapping(path="/cambiar-contrasena", consumes="application/json")
    public ResponseEntity<String> cambiarContrasena(@RequestBody @Valid UsuarioDTO usuario) {
        try {
            this.servicioUsuario.actualizarContrasenia(usuario);
            return ResponseEntity.status(HttpStatus.OK).body("Contraseña actualizada correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Credenciales incorrectas.");
        }
     
    }
	
}
