package com.ecommerce.backend.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.dao.UsuarioDAO;
import com.ecommerce.backend.entidades.Usuario;

@RestController
@RequestMapping(path="/registro", produces="application/json")
public class RegistroREST {
	
	@Autowired
	private UsuarioDAO usuarioDao;
	
    @Autowired
    private PasswordEncoder passwordEncoder;
	
	@PostMapping(consumes="application/json")
	public ResponseEntity<Usuario> registrarUsuario(@RequestBody Usuario usuario) {
		Optional<Usuario> posibleUsuario = usuarioDao.findByUsername(usuario.getUsername());
		if(!posibleUsuario.isEmpty()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}else {
			usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
			usuarioDao.save(usuario);
			return ResponseEntity.status(HttpStatus.OK).body(usuario);
		}
	}
}
