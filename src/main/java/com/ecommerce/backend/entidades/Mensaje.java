package com.ecommerce.backend.entidades;

import java.util.Date;

import lombok.Data;

@Data
public class Mensaje {
	
	private String contenido;
	
	private String emisor;
	
	private Date fecha = new Date();
	

}
