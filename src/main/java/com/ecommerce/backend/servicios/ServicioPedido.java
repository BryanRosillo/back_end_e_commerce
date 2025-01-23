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

/**
 * Servicio encargado de gestionar los pedidos de los usuarios.
 * Este servicio permite la creación de pedidos y la obtención de pedidos realizados por el usuario autenticado.
 */
@Service
public class ServicioPedido {
    
    private final PedidoDAO pedidoDao;
    private final ServicioUsuario servicioUsuario;
    private final ProductoDAO productoDao;
    
    /**
     * Constructor que inyecta las dependencias necesarias para el servicio de pedidos.
     * 
     * @param pedidoDao El DAO encargado de las operaciones sobre los pedidos.
     * @param usuarioDao El DAO encargado de las operaciones sobre los usuarios.
     * @param productoDao El DAO encargado de las operaciones sobre los productos.
     * @param servicioUsuario El servicio encargado de gestionar los usuarios.
     */    
    @Autowired
    private ServicioPedido(PedidoDAO pedidoDao, UsuarioDAO usuarioDao, ProductoDAO productoDao, ServicioUsuario servicioUsuario) {
		this.pedidoDao = pedidoDao;
		this.servicioUsuario = servicioUsuario;
		this.productoDao = productoDao;
	}

    /**
     * Obtiene la lista de pedidos realizados por el usuario autenticado.
     * 
     * @return Una lista de objetos {@link Pedido} asociados al usuario autenticado.
     * @throws Exception Si ocurre algún error al obtener los pedidos del usuario.
     */    
	public List<Pedido> obtenerPedidosPorUsuario() throws Exception {
        // Obtener el usuario autenticado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        // Verificar si el usuario existe
        Usuario usuario = this.servicioUsuario.buscarUsuarioPorUsername(username);

        // Obtener los pedidos del usuario
        return usuario.getPedidos();
    }

    /**
     * Crea un nuevo pedido en el sistema.
     * Valida la existencia de los productos solicitados y los asocia al pedido.
     * 
     * @param pedidoRequest El objeto {@link PedidoDTO} que contiene la información del pedido a crear.
     * @throws Exception Si ocurre algún error al crear el pedido o si algunos productos no se encuentran.
     */    
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
