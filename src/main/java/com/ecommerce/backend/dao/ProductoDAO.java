package com.ecommerce.backend.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.ecommerce.backend.entidades.Producto;

/**
 * Interfaz para acceder a los datos de los productos en la base de datos.
 * Extiende {@code CrudRepository} de Spring Data, lo que proporciona operaciones básicas
 * de persistencia como guardar, eliminar y buscar entidades {@code Producto}.
 */
public interface ProductoDAO extends CrudRepository<Producto, Long>  {
	
	/**
	 * Guarda un nuevo producto en la base de datos o actualiza un producto existente.
	 * 
	 * @param producto El producto a guardar o actualizar.
	 * @return El producto guardado o actualizado.
	 */	
	Producto save(Producto producto);
	
	/**
	 * Busca un producto por su identificador único.
	 * 
	 * @param id El identificador único del producto.
	 * @return Un {@link Optional} que contiene el producto encontrado, o {@code Optional.empty()} si no se encuentra.
	 */	
	Optional<Producto> findById(Long id);

	/**
	 * Obtiene todos los productos de la base de datos.
	 * 
	 * @return Una lista iterable de todos los productos.
	 */	
	Iterable<Producto> findAll();
	
	/**
	 * Busca todos los productos asociados a un usuario específico.
	 * Este método utiliza el identificador de usuario para filtrar los productos.
	 * 
	 * @param id_usuario El identificador del usuario cuyos productos se desean obtener.
	 * @return Una lista de productos asociados al usuario con el id especificado.
	 */
	@Query("SELECT p FROM Producto p WHERE p.usuario.id = :id_usuario")
	List<Producto> encontrarProductosPorUsuario(@Param("id_usuario")Long id_usuario);

	
}
