package com.ecommerce.backend.entidades;
import java.util.List;

import lombok.Data;
/* */
@Data
public class PedidoDTO {
  private Long id_usuario;          // ID del usuario que realiza el pedido
  private List<Long> productosIds;  // Lista de IDs de los productos
}
