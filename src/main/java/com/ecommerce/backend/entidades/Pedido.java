package com.ecommerce.backend.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.springframework.data.rest.core.annotation.RestResource;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
@RestResource(rel="pedidos", path="pedidos")
public class Pedido implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id_pedido;
	
	private Date fechaPedido;
	
	private double totalDinero;
	
	@ManyToMany
	private List<Producto> productos;
	
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private Usuario usuario;
	
    
}
