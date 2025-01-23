package com.ecommerce.backend.servicios;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.ecommerce.backend.dao.PedidoDAO;
import com.ecommerce.backend.dao.ProductoDAO;
import com.ecommerce.backend.dao.UsuarioDAO;
import com.ecommerce.backend.entidades.Pedido;
import com.ecommerce.backend.entidades.PedidoDTO;
import com.ecommerce.backend.entidades.Producto;
import com.ecommerce.backend.entidades.Usuario;
import com.ecommerce.backend.excepciones.ExcepcionPedido;

@Service
public class ServicioPedido {
    
    private final PedidoDAO pedidoDao;
    private final ServicioUsuario servicioUsuario;
    private final ProductoDAO productoDao;
    
    @Autowired
    private ServicioPedido(PedidoDAO pedidoDao, UsuarioDAO usuarioDao, ProductoDAO productoDao, ServicioUsuario servicioUsuario) {
		this.pedidoDao = pedidoDao;
		this.servicioUsuario = servicioUsuario;
		this.productoDao = productoDao;
	}

	public List<Pedido> obtenerPedidosPorUsuario() throws Exception {
        // Obtener el usuario autenticado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        // Verificar si el usuario existe
        Usuario usuario = this.servicioUsuario.buscarUsuarioPorUsername(username);

        // Obtener los pedidos del usuario
        return usuario.getPedidos();
    }

    public void crearPedido(PedidoDTO pedidoRequest) throws Exception {
        Usuario usuario = this.servicioUsuario.buscarUsuarioPorId(pedidoRequest.getId_usuario());

        // Validar y obtener productos
        List<Producto> productos = StreamSupport
                .stream(productoDao.findAllById(pedidoRequest.getProductosIds()).spliterator(), false)
                .collect(Collectors.toList());

        if (productos.isEmpty() || productos.size() != pedidoRequest.getProductosIds().size()) {
            throw new ExcepcionPedido("Algunos productos no fueron encontrados");
        }

        // Crear y guardar el pedido
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setProductos(productos);
        pedido.setFechaPedido(new Date());
        pedido.setTotalDinero(productos.stream().mapToDouble(Producto::getPrecio).sum());

        pedidoDao.save(pedido);
    }
}
