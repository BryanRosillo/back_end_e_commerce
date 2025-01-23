package com.ecommerce.backend.controladores;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.ecommerce.backend.entidades.Mensaje;

/**
 * Controlador encargado de gestionar la comunicación de mensajes a través de WebSocket.
 * Este controlador recibe mensajes enviados desde el cliente y los distribuye a todos
 * los suscriptores del tema "/topic/mensajes".
 */
@Controller
public class ControladorChat {
	
	/**
	 * Método encargado de recibir un mensaje enviado desde el cliente.
	 * El mensaje es enviado a través de WebSocket y luego se distribuye a todos
	 * los clientes suscritos al tema "/topic/mensajes".
	 * 
	 * @param mensaje El mensaje enviado por el cliente.
	 * @return El mismo mensaje, que será distribuido a los suscriptores del tema.
	 */	
	@MessageMapping("/enviarMensaje")
	@SendTo("/topic/mensajes")
	public Mensaje enviarMensaje(Mensaje mensaje) {
		return mensaje;
	}
}
