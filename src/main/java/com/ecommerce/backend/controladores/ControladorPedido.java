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

@RestController
@RequestMapping(path = "/pedidos", produces = "application/json")
public class ControladorPedido {
	
    private final ServicioPedido servicioPedido;
    
    @Autowired
    private ControladorPedido(ServicioPedido servicioPedido) {
		this.servicioPedido = servicioPedido;
	}

	@GetMapping("/usuario")
    public ResponseEntity<Object> obtenerPedidosPorUsuario() {
        try {
            List<Pedido> pedidos = servicioPedido.obtenerPedidosPorUsuario();

            if (pedidos.isEmpty()) {
                return ResponseEntity.ok("El usuario no tiene pedidos.");
            }

            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al obtener los pedidos: " + e.getMessage());
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<String> crearPedido(@RequestBody PedidoDTO pedidoRequest) {
        try {
            String mensaje = servicioPedido.crearPedido(pedidoRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el pedido: " + e.getMessage());
        }
    }
}
