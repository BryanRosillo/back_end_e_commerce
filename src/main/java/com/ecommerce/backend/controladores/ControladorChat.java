package com.ecommerce.backend.controladores;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.ecommerce.backend.entidades.MensajeChat;

@Controller
public class ControladorChat {
	
	@MessageMapping("/sendMessage")
	public void enviarMensaje(MensajeChat mensaje, SimpMessagingTemplate plantilla) {
		String destino = "/queue/user-"+mensaje;
		plantilla.convertAndSend(destino, mensaje);
	}
}
