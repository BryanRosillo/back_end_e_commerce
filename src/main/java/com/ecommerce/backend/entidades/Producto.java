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
import lombok.Data;

@Data
@Entity
@RestResource(rel="productos", path="productos")
public class Producto implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
	
	private String nombre;
	private double precio;
	private String descripcion;
	
	@Lob
	private byte[] imagen;
	
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private Usuario usuario;
	

}
