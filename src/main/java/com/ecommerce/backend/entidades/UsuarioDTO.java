package com.ecommerce.backend.entidades;

import com.ecommerce.backend.anotaciones.PasswordAdecuado;

import lombok.Data;

@Data
public class UsuarioDTO {
    private String username;

    @PasswordAdecuado(message = "El password ingresado no es muy seguro.")
    private String nuevaContrasena;
    
    private String preguntaSeguridad;
}
