package com.ecommerce.backend.dao;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import com.ecommerce.backend.entidades.Usuario;

public interface UsuarioDAO extends CrudRepository<Usuario, Long> {
	
	Usuario save(Usuario usuario);
	Optional<Usuario> findByUsername(String username);
	Optional<Usuario> findById(Long id);
	
}
