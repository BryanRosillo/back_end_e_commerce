package com.ecommerce.backend.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ecommerce.backend.entidades.Pedido;
import com.ecommerce.backend.entidades.Usuario;

/**
 * Interfaz para acceder a los datos de los pedidos en la base de datos.
 * Extiende {@code CrudRepository} de Spring Data, lo que proporciona operaciones básicas
 * de persistencia como guardar, eliminar y buscar entidades {@code Pedido}.
 */
public interface PedidoDAO extends CrudRepository<Pedido,Long> {

    /**
     * Obtiene una lista de pedidos realizados por un usuario específico.
     * Este método utiliza el atributo {@code Usuario} para filtrar los pedidos.
     * 
     * @param usuario El usuario cuyo historial de pedidos se desea obtener.
     * @return Una lista de pedidos asociados al usuario especificado.
     */    
    List<Pedido> findByUsuario(Usuario usuario);
}
