package com.ecommerce.backend.entidades;

import java.io.Serializable;
import java.util.List;

import com.ecommerce.backend.anotaciones.PasswordAdecuado;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Representa un usuario dentro del sistema de e-commerce.
 * Incluye información personal, como correo electrónico, nombre de usuario, contraseña y dirección.
 * Además, está relacionado con productos y pedidos que el usuario ha creado.
 * Esta clase está marcada como una entidad JPA y es serializable.
 */
@Data
@Entity
public class Usuario implements Serializable {
	
	/**
	 * Identificador único del usuario.
	 * Se genera automáticamente mediante la estrategia {@code GenerationType.IDENTITY}.
	 */	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_usuario;
	
	/**
	 * Correo electrónico del usuario.
	 * Debe tener un formato válido de email.
	 */	
	@Email(message = "El email del usuario no es válido.")
	private String correo;
	
	/**
	 * Nombre de usuario único para identificar al usuario dentro del sistema.
	 * No puede ser nulo ni estar vacío.
	 */	
	@NotBlank(message = "El username no puede ser nulo.")
	private String username;
	
	/**
	 * Contraseña del usuario.
	 * No puede ser nula ni vacía.
	 * La contraseña debe cumplir con ciertas reglas de seguridad definidas por la anotación {@code @PasswordAdecuado}.
	 */	
	@NotBlank(message = "El password del usuario no puede ser nulo.")
	@PasswordAdecuado(message = "El password ingresado no es muy seguro.")
	private String password;
	
	/**
	 * Dirección física del usuario.
	 * No puede ser nula ni vacía.
	 */	
	@NotBlank(message = "La dirección del usuario no puede ser nula.")
	private String direccion;
	
	/**
	 * Número de teléfono del usuario.
	 * Este atributo es opcional y puede ser nulo.
	 */	
	private String telefono;
	
	/**
	 * Lista de productos asociados al usuario.
	 * Relación {@code OneToMany} con la entidad {@code Producto}.
	 * Este atributo se ignora durante la serialización/deserialización JSON.
	 */	
	@OneToMany(mappedBy="usuario")
	@JsonIgnore
	private List<Producto> productos;
	
	/**
	 * Lista de pedidos realizados por el usuario.
	 * Relación {@code OneToMany} con la entidad {@code Pedido}.
	 * Este atributo se ignora durante la serialización/deserialización JSON.
	 */	
	@OneToMany(mappedBy="usuario")
	@JsonIgnore
	private List<Pedido> pedidos;
	
	/**
	 * Pregunta de seguridad asociada al usuario para recuperar la cuenta.
	 * Este atributo es opcional.
	 */	
	private String preguntaSeguridad;
	
}
