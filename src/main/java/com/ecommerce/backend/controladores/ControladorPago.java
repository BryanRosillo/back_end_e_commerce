package com.ecommerce.backend.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.servicios.ServicioPaypal;

@RestController
@RequestMapping("/paypal")
public class ControladorPago {
	
	@Autowired
	private ServicioPaypal servicioPaypal;
	
	@PostMapping("/pagar")
	public String crearPago(@RequestParam Double total) {
	    return this.servicioPaypal.solicitarPago(total);
	}

	@GetMapping("/exito")
	public String success(@RequestParam String paymentId, @RequestParam String PayerID) {
		return this.servicioPaypal.procesarPago(paymentId, PayerID);
	}
	
    @GetMapping("/cancelar")
    public String cancel() {
        return "El pago fue cancelado.";
    }
	

}
