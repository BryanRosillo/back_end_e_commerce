package com.ecommerce.backend.configuraciones;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Configuración para el manejo de WebSockets en la aplicación.
 * Habilita la funcionalidad de mensajería en tiempo real mediante WebSocket
 * utilizando el protocolo STOMP y configura los endpoints y el broker de mensajes.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Value("${front.dominio}")
	private String FRONT_DOMINIO;
	
	/**
	 * Registra el endpoint de WebSocket para el chat.
	 * Este es el punto de acceso para los clientes que desean conectar
	 * con el servidor WebSocket a través de STOMP.
	 * 
	 * @param configuracion El registro de endpoints STOMP.
	 */	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry configuracion) {
		configuracion.addEndpoint("/chat-websocket")
		.setAllowedOrigins(this.FRONT_DOMINIO)
		.withSockJS();
	}

	/**
	 * Configura el broker de mensajes para la mensajería STOMP.
	 * Habilita un broker simple para manejar los mensajes destinados a los temas 
	 * que empiezan con "/topic" y configura el prefijo para las rutas de las aplicaciones 
	 * con el prefijo "/app".
	 * 
	 * @param configuracion La configuración del broker de mensajes STOMP.
	 */	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry configuracion) {
		configuracion.enableSimpleBroker("/topic");
		configuracion.setApplicationDestinationPrefixes("/app");
	}
	
}
