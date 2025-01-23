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

/**
 * Representa un pedido realizado por un usuario en el sistema de e-commerce.
 * Contiene información sobre los productos comprados, el total del pedido y el usuario asociado.
 * Esta clase está marcada como una entidad JPA y es serializable.
 */
@Data
@Entity
public class Pedido implements Serializable {
	
	/**
	 * Identificador único del pedido.
	 * Se genera automáticamente mediante la estrategia {@code GenerationType.AUTO}.
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id_pedido;
	
	/**
	 * Fecha en la que se realizó el pedido.
	 * Por defecto, se inicializa con la fecha y hora actuales.
	 */
	private Date fechaPedido = new Date();
	
	/**
	 * Total de dinero correspondiente al pedido.
	 * Debe ser un valor positivo.
	 */	
	@Positive(message = "El valor total del pedido debe ser positivo.")
	private double totalDinero;
	
	/**
	 * Lista de productos incluidos en el pedido.
	 * Este atributo se ignora durante la serialización/deserialización JSON.
	 */	
	@ManyToMany
	@JsonIgnore
	private List<Producto> productos;
	
	/**
	 * Usuario asociado con el pedido.
	 * Este atributo representa la relación {@code ManyToOne} con la entidad {@code Usuario}.
	 * Se ignora durante la serialización/deserialización JSON.
	 */	
	@ManyToOne
	@JoinColumn(name="id_usuario")
	@JsonIgnore
	private Usuario usuario;
	
}
