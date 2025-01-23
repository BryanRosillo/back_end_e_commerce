package com.ecommerce.backend.entidades;
import java.util.List;

import lombok.Data;

/**
 * DTO (Data Transfer Object) que representa los datos necesarios para crear un pedido.
 * Esta clase se utiliza para transferir información desde el cliente hacia el servidor
 * de manera simplificada y estructurada.
 */
@Data
public class PedidoDTO {
  
  /**
   * Identificador único del usuario que realiza el pedido.
   */  
  private Long id_usuario;
  
  /**
   * Lista de identificadores únicos de los productos incluidos en el pedido.
   */  
  private List<Long> productosIds;
}
