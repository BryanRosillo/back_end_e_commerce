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

/**
 * Controlador encargado de gestionar las operaciones relacionadas con el registro y la actualización de usuarios.
 * Incluye funcionalidades para registrar nuevos usuarios y actualizar contraseñas.
 */
@RestController
@RequestMapping(produces="application/json")
public class ControladorRegistro {
	
    private final ServicioUsuario servicioUsuario;

    /**
     * Constructor que inyecta el servicio que maneja la lógica de usuario.
     * 
     * @param servicioUsuario El servicio que maneja la lógica de los usuarios.
     */    
	@Autowired
	public ControladorRegistro(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }

    /**
     * Método que registra un nuevo usuario en el sistema.
     * 
     * @param usuario El usuario a ser registrado, proporcionado en formato JSON.
     * @return Una respuesta indicando si el registro fue exitoso o si hubo un error.
     */    
    @PostMapping(path="/registro", consumes="application/json")
	public ResponseEntity<Object> registrarUsuario(@RequestBody @Valid Usuario usuario) {
        try{
            this.servicioUsuario.registrarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.OK).body("Usuario creado con éxito");
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
	}
	
    /**
     * Método que permite actualizar la contraseña de un usuario.
     * 
     * @param usuario El objeto que contiene los nuevos datos para actualizar la contraseña del usuario.
     * @return Una respuesta indicando si la actualización de la contraseña fue exitosa o si hubo un error.
     */    
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
