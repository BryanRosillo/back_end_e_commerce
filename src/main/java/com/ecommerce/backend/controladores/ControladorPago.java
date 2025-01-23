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

/**
 * Controlador encargado de gestionar las operaciones de pago a través de PayPal.
 * Este controlador permite crear un pago, procesar un pago exitoso y manejar la cancelación
 * del pago desde el cliente.
 */
@RestController
@RequestMapping("/paypal")
public class ControladorPago {

	private final ServicioPaypal servicioPaypal;
	
	@Value("${front.dominio}")
	private String FRONT_DOMINIO;
		
	/**
	 * Constructor que inyecta el servicio de PayPal.
	 * 
	 * @param servicioPaypal El servicio que maneja la lógica de pagos de PayPal.
	 */	
	@Autowired
	private ControladorPago(ServicioPaypal servicioPaypal) {
		this.servicioPaypal = servicioPaypal;
	}

	/**
	 * Método que recibe el total del pago y solicita la creación de un pago en PayPal.
	 * 
	 * @param total El monto total que el usuario desea pagar.
	 * @return Un enlace o token generado para redirigir al usuario a la página de pago de PayPal.
	 */	
	@PostMapping("/pagar")
	public String crearPago(@RequestParam Double total) {
	    return this.servicioPaypal.solicitarPago(total);
	}

	/**
	 * Método que maneja la respuesta de PayPal después de que el usuario ha completado un pago.
	 * Procesa el pago y redirige al usuario a la página de éxito con un parámetro de estado.
	 * 
	 * @param paymentId El identificador del pago proporcionado por PayPal.
	 * @param PayerID El identificador del pagador proporcionado por PayPal.
	 * @return Una redirección a la página del frontend con el estado del pago (éxito).
	 */	
	@GetMapping(path="/exito")
	public RedirectView efectuarPago(@RequestParam String paymentId, @RequestParam String PayerID) {
		this.servicioPaypal.procesarPago(paymentId, PayerID);
		return new RedirectView(this.FRONT_DOMINIO+"?status=exito");
	}
	
	/**
	 * Método que maneja la cancelación de un pago por parte del usuario.
	 * Redirige al usuario a la página del frontend con un parámetro de estado de cancelación.
	 * 
	 * @return Una redirección a la página del frontend con el estado del pago (cancelado).
	 */	
    @GetMapping(path="/cancelar")
    public RedirectView cancelarPago() {
    	return new RedirectView(this.FRONT_DOMINIO+"?status=cancelado");
    }
	

}
