package com.ecommerce.backend.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.dao.UsuarioDAO;
import com.ecommerce.backend.entidades.Usuario;
import com.ecommerce.backend.entidades.UsuarioDTO;

@RestController
@RequestMapping(produces="application/json")
public class RegistroREST {
	
	@Autowired
	private UsuarioDAO usuarioDao;
	
    @Autowired
    private PasswordEncoder passwordEncoder;
	
	@PostMapping(path="/registro", consumes="application/json")
	public ResponseEntity<Usuario> registrarUsuario(@RequestBody Usuario usuario) {
		Optional<Usuario> posibleUsuario = usuarioDao.findByUsername(usuario.getUsername());
		if(!posibleUsuario.isEmpty()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}else {
			usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
			usuario.setPreguntaSeguridad(passwordEncoder.encode(usuario.getPreguntaSeguridad()));
			usuarioDao.save(usuario);
			return ResponseEntity.status(HttpStatus.OK).body(usuario);
		}
	}
	
    @PatchMapping(path="/cambiar-contrasena", consumes="application/json")
    public ResponseEntity<String> cambiarContrasena(@RequestBody UsuarioDTO usuario) {
        Optional<Usuario> usuarioOpt = usuarioDao.findByUsername(usuario.getUsername());

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }
        
        Usuario usuarioActualizar = usuarioOpt.get();
        
        if(!passwordEncoder.matches(usuario.getPreguntaSeguridad(), usuarioActualizar.getPreguntaSeguridad())) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Credenciales incorrectas.");
        }
        
        usuarioActualizar.setPassword(passwordEncoder.encode(usuario.getNuevaContrasena()));
        usuarioDao.save(usuarioActualizar);
        return ResponseEntity.status(HttpStatus.OK).body("Contraseña actualizada correctamente.");
    }
	
}
