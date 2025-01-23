package com.ecommerce.backend.dao;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import com.ecommerce.backend.entidades.Usuario;

/**
 * Interfaz para acceder a los datos de los usuarios en la base de datos.
 * Extiende {@code CrudRepository} de Spring Data, lo que proporciona operaciones básicas
 * de persistencia como guardar, eliminar y buscar entidades {@code Usuario}.
 */
public interface UsuarioDAO extends CrudRepository<Usuario, Long> {
	
	/**
	 * Guarda un nuevo usuario en la base de datos o actualiza un usuario existente.
	 * 
	 * @param usuario El usuario a guardar o actualizar.
	 * @return El usuario guardado o actualizado.
	 */	
	Usuario save(Usuario usuario);
	
	/**
	 * Busca un usuario por su nombre de usuario (username).
	 * 
	 * @param username El nombre de usuario que se desea buscar.
	 * @return Un {@link Optional} que contiene el usuario encontrado, o {@code Optional.empty()} si no se encuentra.
	 */	
	Optional<Usuario> findByUsername(String username);
	
	/**
	 * Busca un usuario por su identificador único.
	 * 
	 * @param id El identificador único del usuario.
	 * @return Un {@link Optional} que contiene el usuario encontrado, o {@code Optional.empty()} si no se encuentra.
	 */	
	Optional<Usuario> findById(Long id);
}
