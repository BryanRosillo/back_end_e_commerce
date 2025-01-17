package com.ecommerce.backend.servicios;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ecommerce.backend.dao.PedidoDAO;
import com.ecommerce.backend.dao.ProductoDAO;
import com.ecommerce.backend.dao.UsuarioDAO;
import com.ecommerce.backend.entidades.Pedido;
import com.ecommerce.backend.entidades.PedidoDTO;
import com.ecommerce.backend.entidades.Producto;
import com.ecommerce.backend.entidades.Usuario;

@Service
public class ServicioPedido {
    
    @Autowired
    private PedidoDAO pedidoDao;
    
    @Autowired
    private UsuarioDAO usuarioDao;
    
    @Autowired
    private ProductoDAO productoDao;

    public List<Pedido> obtenerPedidosPorUsuario(Long idUsuario) {
        // Verificar si el usuario existe
        Usuario usuario = usuarioDao.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Obtener los pedidos del usuario
        return usuario.getPedidos();
    }

    public String crearPedido(PedidoDTO pedidoRequest) {
        // Validar usuario
        Optional<Usuario> usuarioOpt = usuarioDao.findById(pedidoRequest.getId_usuario());
        if (usuarioOpt.isEmpty()) {
            return "Usuario no encontrado";
        }
        Usuario usuario = usuarioOpt.get();

        // Validar y obtener productos
        List<Producto> productos = StreamSupport
                .stream(productoDao.findAllById(pedidoRequest.getProductosIds()).spliterator(), false)
                .collect(Collectors.toList());

        if (productos.isEmpty() || productos.size() != pedidoRequest.getProductosIds().size()) {
            return "Algunos productos no fueron encontrados";
        }

        // Crear y guardar el pedido
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setProductos(productos);
        pedido.setFechaPedido(new Date());
        pedido.setTotalDinero(productos.stream().mapToDouble(Producto::getPrecio).sum());

        pedidoDao.save(pedido);
        return "Pedido creado exitosamente con ID: " + pedido.getId_pedido();
    }
}
