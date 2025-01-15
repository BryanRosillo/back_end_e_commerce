package com.ecommerce.backend.entidades;

import java.io.Serializable;
import org.springframework.data.rest.core.annotation.RestResource;
import jakarta.persistence.Entity;
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

@Data
@Entity
@RestResource(rel="productos", path="productos")
public class Producto implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
	
	@NotBlank(message = "El nombre del producto no puede ser nulo.")
	private String nombre;
	
	@NotNull(message = "El precio del producto no puede ser nulo.")
	@Positive(message = "El precio del producto debe ser un valor positivo.")
	private double precio;

	@NotBlank(message = "La descripción del producto no puede ser nulo.")
	private String descripcion;
	
	@Lob
	private byte[] imagen;
	
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private Usuario usuario;
	

}
