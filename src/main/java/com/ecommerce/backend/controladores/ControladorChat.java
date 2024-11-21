package com.ecommerce.backend.controladores;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.ecommerce.backend.entidades.MensajeChat;

@Controller
public class ControladorChat {
	
	@MessageMapping("/sendMessage")
	@SendTo("/topic/messages")
	public MensajeChat enviarMensaje(MensajeChat mensaje) {
		return mensaje;
	}
}
