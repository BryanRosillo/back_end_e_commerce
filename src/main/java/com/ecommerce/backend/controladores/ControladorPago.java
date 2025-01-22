package com.ecommerce.backend.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.ecommerce.backend.servicios.ServicioPaypal;

@RestController
@RequestMapping("/paypal")
public class ControladorPago {

	private final ServicioPaypal servicioPaypal;
	
	@Value("${front.dominio}")
	private String FRONT_DOMINIO;
		
	@Autowired
	private ControladorPago(ServicioPaypal servicioPaypal) {
		this.servicioPaypal = servicioPaypal;
	}

	@PostMapping("/pagar")
	public String crearPago(@RequestParam Double total) {
	    return this.servicioPaypal.solicitarPago(total);
	}

	@GetMapping(path="/exito")
	public RedirectView efectuarPago(@RequestParam String paymentId, @RequestParam String PayerID) {
		this.servicioPaypal.procesarPago(paymentId, PayerID);
		return new RedirectView(this.FRONT_DOMINIO+"?status=exito");
	}
	
    @GetMapping(path="/cancelar")
    public RedirectView cancelarPago() {
    	return new RedirectView(this.FRONT_DOMINIO+"?status=cancelado");
    }
	

}
