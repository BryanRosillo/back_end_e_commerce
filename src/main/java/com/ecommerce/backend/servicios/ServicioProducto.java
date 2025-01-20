package com.ecommerce.backend.servicios;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ecommerce.backend.dao.ProductoDAO;
import com.ecommerce.backend.dao.UsuarioDAO;
import com.ecommerce.backend.entidades.Producto;
import com.ecommerce.backend.entidades.Usuario;
import com.ecommerce.backend.excepciones.ExcepcionProducto;

@Service
public class ServicioProducto {

    private final ProductoDAO productoDao;
    private final UsuarioDAO usuarioDao;

    @Autowired
    public ServicioProducto(ProductoDAO productoDao, UsuarioDAO usuarioDAO) {
        this.productoDao = productoDao;
        this.usuarioDao = usuarioDAO;
    }

    public Producto buscarProductoId(Long id) throws ExcepcionProducto{
        return productoDao.findById(id)
                            .orElseThrow(() -> new ExcepcionProducto("Producto no encontrado"));
    }

    public Producto guardaProducto(Producto producto){
        return this.productoDao.save(producto);
    }

    @Transactional(readOnly = true)
    public List<Producto> obtenerProductosPorUsuario() {
        // Obtener el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        // Buscar al usuario en la base de datos
        Usuario usuario = usuarioDao.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado")
                );
        return productoDao.encontrarProductosPorUsuario(usuario.getId_usuario());
    }

    public Producto editarProducto(Long id, Producto productoEditado) {
        Producto productoExistente = productoDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Actualizar los campos del producto
        productoExistente.setNombre(productoEditado.getNombre());
        productoExistente.setPrecio(productoEditado.getPrecio());
        productoExistente.setDescripcion(productoEditado.getDescripcion());

        // Si la imagen fue proporcionada, también se actualiza
        if (productoEditado.getImagen() != null && productoEditado.getImagen().length > 0) {
            productoExistente.setImagen(productoEditado.getImagen());
        }

        // Guardar cambios
        return productoDao.save(productoExistente);
    }

    public Iterable<Producto> devolverProductos(){
        return this.productoDao.findAll();
    }
}
