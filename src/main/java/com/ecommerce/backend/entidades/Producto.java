package com.ecommerce.backend.entidades;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * Representa un producto disponible en el sistema de e-commerce.
 * Incluye información como el nombre, precio, descripción, imagen y usuario propietario.
 * Esta clase está marcada como una entidad JPA y es serializable.
 */
@Data
@Entity
public class Producto implements Serializable {
	
	/**
	 * Identificador único del producto.
	 * Se genera automáticamente mediante la estrategia {@code GenerationType.IDENTITY}.
	 */	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
	
	/**
	 * Nombre del producto.
	 * No puede ser nulo ni estar vacío.
	 */	
	@NotBlank(message = "El nombre del producto no puede ser nulo.")
	private String nombre;
	
	/**
	 * Precio del producto.
	 * No puede ser nulo y debe ser un valor positivo.
	 */	
	@NotNull(message = "El precio del producto no puede ser nulo.")
	@Positive(message = "El precio del producto debe ser un valor positivo.")
	private double precio;

	/**
	 * Descripción detallada del producto.
	 * No puede ser nula ni estar vacía.
	 */	
	@NotBlank(message = "La descripción del producto no puede ser nulo.")
	private String descripcion;
	
	/**
	 * Imagen del producto almacenada como un arreglo de bytes.
	 * Se carga de manera diferida utilizando {@code FetchType.LAZY}.
	 */	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] imagen;
	
	/**
	 * Usuario propietario del producto.
	 * Representa la relación {@code ManyToOne} con la entidad {@code Usuario}.
	 * Este atributo se ignora durante la serialización/deserialización JSON.
	 */	
	@ManyToOne
	@JoinColumn(name="id_usuario")
	@JsonIgnore
	private Usuario usuario;
	

}
