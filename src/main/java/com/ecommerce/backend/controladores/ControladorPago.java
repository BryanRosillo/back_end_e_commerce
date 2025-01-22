package com.ecommerce.backend.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.servicios.ServicioPaypal;

@RestController
@RequestMapping("/paypal")
public class ControladorPago {

	private final ServicioPaypal servicioPaypal;
		
	@Autowired
	private ControladorPago(ServicioPaypal servicioPaypal) {
		this.servicioPaypal = servicioPaypal;
	}

	@PostMapping("/pagar")
	public String crearPago(@RequestParam Double total) {
	    return this.servicioPaypal.solicitarPago(total);
	}

	@GetMapping(path="/exito", produces="application/json")
	public ResponseEntity<Object> success(@RequestParam String paymentId, @RequestParam String PayerID) {
		return ResponseEntity.ok(this.servicioPaypal.procesarPago(paymentId, PayerID));
	}
	
    @GetMapping(path="/cancelar", produces="application/json")
    public ResponseEntity<Object> cancel() {
        return ResponseEntity.badRequest().body("El pago fue cancelado.");
    }
	

}
