package com.ecommerce.backend.anotaciones;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validador personalizado para la validación de contraseñas.
 * Este validador asegura que las contraseñas cumplan con ciertos requisitos de seguridad:
 * - Longitud mínima de 8 caracteres.
 * - Al menos una letra minúscula, una mayúscula, un número y un carácter especial.
 * 
 * Implementa la interfaz {@link ConstraintValidator} para validar la contraseña basada en la anotación {@link PasswordAdecuado}.
 */
public class ValidadorPassword implements ConstraintValidator<PasswordAdecuado, String>{

    /**
     * Método que valida si la contraseña proporcionada cumple con los requisitos de seguridad.
     * 
     * @param password La contraseña que se va a validar.
     * @param context El contexto de validación que permite configurar el comportamiento de la validación.
     * @return {@code true} si la contraseña es válida, {@code false} si no cumple con los requisitos.
     */    
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }

        // Verifica que la contraseña tenga al menos 8 caracteres
        if (password.length() < 8) {
            return false;
        }

        // Expresión regular que requiere al menos:
        // - Una letra minúscula
        // - Una letra mayúscula
        // - Un número
        // - Un carácter especial (@, $, !, %, *, ?, &)
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

        // Valida si la contraseña coincide con la expresión regular
        return password.matches(regex);
    }

    
    
}
