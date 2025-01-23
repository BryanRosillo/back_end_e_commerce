package com.ecommerce.backend.entidades;

import lombok.Data;

/**
 * Representa la respuesta de un servicio de autenticación que contiene un token de acceso.
 * Este objeto es utilizado para almacenar información relacionada con el token
 * necesario para acceder a los recursos protegidos del sistema.
 */
@Data
public class RespuestaTokenAcceso {
    
    /**
     * Token de acceso generado por el servicio de autenticación.
     * Este token se utiliza para autenticar las solicitudes posteriores.
     */    
    private String access_token;
    
    /**
     * Tipo de token de acceso (por ejemplo, "Bearer").
     * Especifica el tipo de esquema de autenticación utilizado.
     */    
    private String token_type;
    
    /**
     * Tiempo en segundos que el token de acceso es válido.
     * Una vez transcurrido este tiempo, el token debe ser renovado.
     */    
    private int expires_in;

}
