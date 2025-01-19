package com.ecommerce.backend.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Entity
public class Pedido implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id_pedido;
	
	private Date fechaPedido = new Date();
	
	@Positive(message = "El valor total del pedido debe ser positivo.")
	private double totalDinero;
	
	@ManyToMany
	@JsonIgnore
	private List<Producto> productos;
	
	@ManyToOne
	@JoinColumn(name="id_usuario")
	@JsonIgnore
	private Usuario usuario;
	
    
}
