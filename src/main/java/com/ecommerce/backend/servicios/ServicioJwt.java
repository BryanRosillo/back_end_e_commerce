package com.ecommerce.backend.servicios;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class ServicioJwt {
	
	private final SecretKey LLAVE_SECRETA;
	
	@Value("${jwt.expiracion}")
	private Long tiempoExpiracion;

	private ServicioJwt(@Value("${jwt.secreto}") String secreto) {
		this.LLAVE_SECRETA = Keys.hmacShaKeyFor(secreto.getBytes());
	}
	
	public String generarToken(String identificador) {
		return Jwts.builder()
				.subject(identificador)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + this.tiempoExpiracion * 1000))
				.signWith(LLAVE_SECRETA, Jwts.SIG.HS256)
				.compact();
	}
	
	public Long extraerId(String token) {
		return Long.parseLong(Jwts.parser()
				.verifyWith(LLAVE_SECRETA)
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject());
	}
	
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
