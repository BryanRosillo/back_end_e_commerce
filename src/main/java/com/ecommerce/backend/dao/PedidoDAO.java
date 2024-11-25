package com.ecommerce.backend.dao;

import org.springframework.data.repository.CrudRepository;

import com.ecommerce.backend.entidades.Pedido;

public interface PedidoDAO extends CrudRepository<Pedido,Long> {

}
