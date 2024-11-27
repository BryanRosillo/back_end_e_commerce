package com.ecommerce.backend.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.dao.UsuarioDAO;
import com.ecommerce.backend.entidades.Usuario;

@RestController
@RequestMapping(path="/login", produces="application/json")
public class LoginREST {
	
	@Autowired
	private UsuarioDAO usuarioDao;
	
    @Autowired
    private PasswordEncoder passwordEncoder;
	
	@PostMapping
	public ResponseEntity<Object> validarCuenta(@RequestBody Usuario usuario) {
		Optional<Usuario> posibleUsuario = usuarioDao.findByUsername(usuario.getUsername());
		if(posibleUsuario.isEmpty()) {
			return ResponseEntity.notFound().build();
		}else {
			Usuario usuarioRegistrado = posibleUsuario.get();
			if(!passwordEncoder.matches(usuario.getPassword(), usuarioRegistrado.getPassword())) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok().body(usuarioRegistrado.getId_usuario());
		}
		
	}
	

}
