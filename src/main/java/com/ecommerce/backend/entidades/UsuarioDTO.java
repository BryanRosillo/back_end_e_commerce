package com.ecommerce.backend.entidades;

import lombok.Data;

@Data
public class UsuarioDTO {
    private String username;
    private String nuevaContrasena;
    private String preguntaSeguridad;
}
