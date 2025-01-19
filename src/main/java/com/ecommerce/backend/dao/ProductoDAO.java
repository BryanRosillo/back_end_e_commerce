package com.ecommerce.backend.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.ecommerce.backend.entidades.Producto;

public interface ProductoDAO extends CrudRepository<Producto, Long>  {
	
	
	Producto save(Producto producto);
	
	Optional<Producto> findById(Long id);
	
	Iterable<Producto> findAll();
	
	@Query("SELECT p FROM Producto p WHERE p.usuario.id = :id_usuario")
	List<Producto> encontrarProductosPorUsuario(@Param("id_usuario")Long id_usuario);

	
}
