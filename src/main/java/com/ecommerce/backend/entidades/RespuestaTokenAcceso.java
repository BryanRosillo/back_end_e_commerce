package com.ecommerce.backend.entidades;

import lombok.Data;

@Data
public class RespuestaTokenAcceso {
    private String access_token;
    private String token_type;
    private int expires_in;

}
