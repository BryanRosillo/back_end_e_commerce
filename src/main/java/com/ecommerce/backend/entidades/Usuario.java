package com.ecommerce.backend.entidades;

import java.io.Serializable;
import java.util.List;

import com.ecommerce.backend.anotaciones.PasswordAdecuado;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
public class Usuario implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_usuario;
	
	@Email(message = "El email del usuario no es válido.")
	private String correo;
	
	@NotBlank(message = "El username no puede ser nulo.")
	private String username;
	
	@NotBlank(message = "El password del usuario no puede ser nulo.")
	@PasswordAdecuado(message = "El password ingresado no es muy seguro.")
	private String password;
	
	@NotBlank(message = "La dirección del usuario no puede ser nula.")
	private String direccion;
	
	private String telefono;
	
	@OneToMany(mappedBy="usuario")
	private List<Producto> productos;
	
	@OneToMany(mappedBy="usuario")
	private List<Pedido> pedidos;
	
	private String preguntaSeguridad;
	
}
