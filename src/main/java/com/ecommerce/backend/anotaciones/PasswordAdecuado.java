package com.ecommerce.backend.anotaciones;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Anotación personalizada para validar la seguridad de una contraseña.
 * Esta anotación se utiliza para asegurarse de que una contraseña cumpla con ciertos requisitos de seguridad
 * establecidos por el validador {@link ValidadorPassword}.
 */
@Constraint(validatedBy = ValidadorPassword.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordAdecuado {
    
    /**
     * Mensaje de error que se muestra cuando la validación de la contraseña falla.
     * 
     * @return El mensaje de error predeterminado.
     */    
    String message() default "El password no es tan segura.";
    
    Class<?>[] groups() default {};
    
    /**
     * Los datos adicionales que se pueden asociar con la validación.
     * 
     * @return Los datos adicionales asociados a la validación.
     */    
    Class<? extends Payload>[] payload() default{};
}
