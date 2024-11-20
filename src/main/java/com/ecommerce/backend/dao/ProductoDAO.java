package com.ecommerce.backend.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ecommerce.backend.entidades.Producto;

public interface ProductoDAO extends CrudRepository<Producto, Long>  {
	
	
	Producto save(Producto producto);
	
	Optional<Producto> findById(Long id);
	
	Iterable<Producto> findAll();
	
	
}
