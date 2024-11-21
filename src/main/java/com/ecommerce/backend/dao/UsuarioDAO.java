package com.ecommerce.backend.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.ecommerce.backend.entidades.Usuario;

@RepositoryRestResource(exported = false)
public interface UsuarioDAO extends CrudRepository<Usuario, Long> {
	
	Usuario save(Usuario usuario);
	Optional<Usuario> findByUsername(String username);
}
