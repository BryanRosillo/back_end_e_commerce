package com.ecommerce.backend.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ecommerce.backend.entidades.Pedido;
import com.ecommerce.backend.entidades.Usuario;

public interface PedidoDAO extends CrudRepository<Pedido,Long> {
    // Método para obtener pedidos por un usuario específico
    List<Pedido> findByUsuario(Usuario usuario);
}
