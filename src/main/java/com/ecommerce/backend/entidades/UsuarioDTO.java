package com.ecommerce.backend.entidades;

import com.ecommerce.backend.anotaciones.PasswordAdecuado;

import lombok.Data;

/**
 * DTO (Data Transfer Object) que representa los datos necesarios para actualizar
 * la información de un usuario, como el nombre de usuario, la nueva contraseña 
 * y la pregunta de seguridad.
 * Esta clase se utiliza para transferir información desde el cliente al servidor.
 */
@Data
public class UsuarioDTO {
    
    /**
     * Nombre de usuario que se desea actualizar.
     * Este atributo se utiliza para identificar al usuario en el sistema.
     */    
    private String username;

    /**
     * Nueva contraseña del usuario.
     * Este atributo debe cumplir con las reglas de seguridad definidas por la anotación {@code @PasswordAdecuado}.
     */    
    @PasswordAdecuado(message = "El password ingresado no es muy seguro.")
    private String nuevaContrasena;
    
    /**
     * Pregunta de seguridad asociada al usuario para la recuperación de cuenta.
     * Este atributo es opcional y puede ser actualizado.
     */    
    private String preguntaSeguridad;
}
