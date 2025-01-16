package com.ecommerce.backend.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ecommerce.backend.dao.ProductoDAO;
import com.ecommerce.backend.entidades.Producto;
import com.ecommerce.backend.excepciones.ExcepcionProducto;

@Service
public class ServicioProducto {

    private final ProductoDAO productoDao;

    @Autowired
    public ServicioProducto(ProductoDAO productoDao) {
        this.productoDao = productoDao;
    }

    public Producto buscarProductoId(Long id) throws ExcepcionProducto{
        return productoDao.findById(id)
                            .orElseThrow(() -> new ExcepcionProducto("Producto no encontrado"));
    }

    public Producto guardaProducto(Producto producto){
        return this.productoDao.save(producto);
    }

    public List<Producto> obtenerProductosPorUsuario(Long id_usuario) {
        return productoDao.encontrarProductosPorUsuario(id_usuario);
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
}
