package com.ecommerce.backend.controladores;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.entidades.Pedido;
import com.ecommerce.backend.entidades.PedidoDTO;
import com.ecommerce.backend.servicios.ServicioPedido;

/**
 * Controlador encargado de gestionar los pedidos realizados por los usuarios.
 * Permite obtener los pedidos de un usuario y crear nuevos pedidos.
 */
@RestController
@RequestMapping(path = "/pedidos", produces = "application/json")
public class ControladorPedido {
	
    private final ServicioPedido servicioPedido;
    
    /**
     * Constructor que inyecta el servicio que maneja la lógica de pedidos.
     * 
     * @param servicioPedido El servicio que maneja la lógica de los pedidos.
     */    
    @Autowired
    private ControladorPedido(ServicioPedido servicioPedido) {
		this.servicioPedido = servicioPedido;
	}

	/**
	 * Método que obtiene todos los pedidos de un usuario específico.
	 * 
	 * @return Una respuesta con los pedidos del usuario, o un mensaje indicando que no tiene pedidos.
	 */    
	@GetMapping("/usuario")
    public ResponseEntity<Object> obtenerPedidosPorUsuario() {
        try {
            List<Pedido> pedidos = servicioPedido.obtenerPedidosPorUsuario();

            if (pedidos.isEmpty()) {
                return ResponseEntity.ok("El usuario no tiene pedidos.");
            }

            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al obtener los pedidos.");
        }
    }

    /**
     * Método que crea un nuevo pedido utilizando los datos proporcionados por el cliente.
     * 
     * @param pedidoRequest Los datos del pedido que se va a crear, encapsulados en un objeto `PedidoDTO`.
     * @return Una respuesta indicando si el pedido fue creado con éxito o si hubo un error.
     */    
    @PostMapping("/crear")
    public ResponseEntity<String> crearPedido(@RequestBody PedidoDTO pedidoRequest) {
        try {
            servicioPedido.crearPedido(pedidoRequest);
            return ResponseEntity.ok("Pedido creado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el pedido.");
        }
    }
}
