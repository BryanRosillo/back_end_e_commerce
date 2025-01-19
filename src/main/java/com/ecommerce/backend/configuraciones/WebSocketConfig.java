package com.ecommerce.backend.configuraciones;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Value("${front.dominio}")
	private String frontDominio;
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry configuracion) {
		configuracion.addEndpoint("/chat-websocket")
		.setAllowedOrigins(this.frontDominio,"http://localhost:*")
		.withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry configuracion) {
		configuracion.enableSimpleBroker("/topic");
		configuracion.setApplicationDestinationPrefixes("/app");
	}
	
}
