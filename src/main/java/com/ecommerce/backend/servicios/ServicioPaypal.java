package com.ecommerce.backend.servicios;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ecommerce.backend.entidades.RespuestaTokenAcceso;

@Service
public class ServicioPaypal {
	
	private static final String PAYPAL_URL = "https://api-m.sandbox.paypal.com/v1/oauth2/token";
	private final String PAYPAL_URL_PAGO = "https://api-m.sandbox.paypal.com/v1/payments/payment";
	
	@Value("${paypal.cliente.id}")
	private String CLIENTE_ID;
	
	@Value("${paypal.cliente.secreto}")
	private String SECRETO_CLIENTE;
	
	private String obtenerTokenAcceso() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders cabeceras = new HttpHeaders();
		cabeceras.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		cabeceras.setBasicAuth(this.CLIENTE_ID, this.SECRETO_CLIENTE);
		HttpEntity<String> peticion = new HttpEntity<>("grant_type=client_credentials",cabeceras);
		ResponseEntity<RespuestaTokenAcceso> respuesta = restTemplate.exchange(this.PAYPAL_URL, HttpMethod.POST, peticion, RespuestaTokenAcceso.class);
		if(respuesta.getStatusCode()==HttpStatus.OK) {
			return respuesta.getBody().getAccess_token();
		}else {
			throw new RuntimeException("No se pudo conseguir el token de acceso de PayPal: " + respuesta.getStatusCode());
		}
	}
	
	public String solicitarPago(Double total) {
	    String token = this.obtenerTokenAcceso();
	    
	    RestTemplate restTemplate = new RestTemplate();
	    HttpHeaders cabeceras = new HttpHeaders();
	    cabeceras.setContentType(MediaType.APPLICATION_JSON);
	    cabeceras.set("Authorization", "Bearer " + token);

	    Map<String, Object> detallesPago = new HashMap<>();
	    detallesPago.put("intent", "sale");

	    Map<String, Object> pagador = new HashMap<>();
	    pagador.put("payment_method", "paypal");
	    detallesPago.put("payer", pagador);

	    BigDecimal totalDecimal = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP);
	    String totalFormateado = totalDecimal.toString();
	    Map<String, Object> detalleCantidad = new HashMap<>();
	    detalleCantidad.put("total", totalFormateado);
	    detalleCantidad.put("currency", "USD");

	    Map<String, Object> transaccion = new HashMap<>();
	    transaccion.put("amount", detalleCantidad); 
	    transaccion.put("description", "Test Payment");

	    detallesPago.put("transactions", Arrays.asList(transaccion)); 

	    Map<String, String> urlsRedireccion = new HashMap<>();
	    urlsRedireccion.put("return_url", "http://localhost:8080/paypal/exito");
	    urlsRedireccion.put("cancel_url", "http://localhost:8080/paypal/cancelar");
	    detallesPago.put("redirect_urls", urlsRedireccion);

	    HttpEntity<Map<String, Object>> peticion = new HttpEntity<>(detallesPago, cabeceras);

	    ResponseEntity<Map> respuesta = restTemplate.postForEntity(this.PAYPAL_URL_PAGO, peticion, Map.class);

	    if (respuesta.getStatusCode() == HttpStatus.CREATED) {
	        List<Map<String, String>> links = (List<Map<String, String>>) respuesta.getBody().get("links");
	        for (Map<String, String> link : links) {
	            if (link.get("rel").equals("approval_url")) {
	                return link.get("href"); 
	            }
	        }
	    } else {
	        throw new RuntimeException("Fallo en crear el pago: " + respuesta.getStatusCode());
	    }
	    
	    return null;
		
	}
	
	public String procesarPago(String paymentId, String PayerID) {
		String tokenAcceso = this.obtenerTokenAcceso();
		
		RestTemplate restTemplate = new RestTemplate();

        String jsonBody = "{"
                + "\"payer_id\":\"" + PayerID + "\""
                + "}";

        HttpHeaders cabeceras = new HttpHeaders();
        cabeceras.set("Authorization", "Bearer " + tokenAcceso);
        cabeceras.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> peticion = new HttpEntity<>(jsonBody, cabeceras);
        ResponseEntity<String> respuesta = restTemplate.exchange(this.PAYPAL_URL_PAGO+"/"+paymentId+"/execute", HttpMethod.POST, peticion, String.class, paymentId);
        return respuesta.getBody();
	}
		
}
