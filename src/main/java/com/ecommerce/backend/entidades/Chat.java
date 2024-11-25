/*package com.ecommerce.backend.entidades;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;


@Data
@Entity
public class Chat {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_chat;
	private String nombreChat;
	
	@ManyToOne
	@JoinColumn(name="id_emisor")
	private Usuario emisor;
	
	@ManyToOne
	@JoinColumn(name="id_receptor")
	private Usuario receptor;
	
	@OneToMany(mappedBy="chat")
	private List<Mensaje> mensajes;
}
*/