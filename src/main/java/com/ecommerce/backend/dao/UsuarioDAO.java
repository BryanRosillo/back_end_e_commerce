package com.ecommerce.backend.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.ecommerce.backend.entidades.Usuario;


public interface UsuarioDAO extends CrudRepository<Usuario, Long> {
	
	@RestResource(exported=false)
	Usuario save(Usuario usuario);
	
	@RestResource(exported=false)
	Optional<Usuario> findByUsername(String username);
}
