package com.ecommerce.backend.entidades;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Usuario implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_usuario;
	
	private String username;
	
	private String password;
	
	private String direccion;
	
	private String telefono;
	
	@OneToMany(mappedBy="usuario")
	private List<Producto> productos;
	
	@OneToMany(mappedBy="usuario")
	private List<Pedido> pedidos;
	
	
}
