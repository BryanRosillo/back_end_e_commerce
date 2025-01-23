package com.ecommerce.backend.servicios;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * Servicio encargado de la generación y validación de tokens JWT (JSON Web Tokens).
 * Este servicio permite generar un token para un usuario, extraer el identificador del usuario
 * de un token y validar si un token es válido.
 */
@Service
public class ServicioJwt {
	
	private final SecretKey LLAVE_SECRETA;
	
	@Value("${jwt.expiracion}")
	private Long tiempoExpiracion;

	/**
	 * Constructor del servicio, que genera la clave secreta a partir del valor configurado
	 * en las propiedades de la aplicación.
	 * 
	 * @param secreto La clave secreta usada para firmar los tokens.
	 */	
	private ServicioJwt(@Value("${jwt.secreto}") String secreto) {
		this.LLAVE_SECRETA = Keys.hmacShaKeyFor(secreto.getBytes());
	}
	
	/**
	 * Genera un token JWT para un usuario identificado por el parámetro proporcionado.
	 * El token incluye el identificador del usuario, la fecha de emisión y la fecha de expiración.
	 * 
	 * @param identificador El identificador del usuario para el que se genera el token.
	 * @return El token JWT generado.
	 */	
	public String generarToken(String identificador) {
		return Jwts.builder()
				.subject(identificador)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + this.tiempoExpiracion * 1000))
				.signWith(LLAVE_SECRETA, Jwts.SIG.HS256)
				.compact();
	}
	
	/**
	 * Extrae el identificador del usuario desde un token JWT.
	 * 
	 * @param token El token del cual se extraerá el identificador.
	 * @return El identificador del usuario extraído del token.
	 */	
	public Long extraerId(String token) {
		return Long.parseLong(Jwts.parser()
				.verifyWith(LLAVE_SECRETA)
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject());
	}
	
	/**
	 * Valida si un token JWT es válido, es decir, si la firma del token es correcta
	 * y si no ha expirado.
	 * 
	 * @param token El token JWT que se desea validar.
	 * @return true si el token es válido, false si no lo es.
	 */	
	public boolean validarToken(String token) {
		try {
			Jwts.parser()
				.verifyWith(LLAVE_SECRETA)
				.build()
				.parseSignedClaims(token);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	

}
