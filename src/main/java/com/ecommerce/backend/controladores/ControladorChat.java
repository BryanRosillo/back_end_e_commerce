package com.ecommerce.backend.controladores;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.ecommerce.backend.entidades.Mensaje;

@Controller
public class ControladorChat {
	
	
	@MessageMapping("/enviarMensaje")
	@SendTo("/topic/mensajes")
	public Mensaje enviarMensaje(Mensaje mensaje) {
		return mensaje;
	}
}
