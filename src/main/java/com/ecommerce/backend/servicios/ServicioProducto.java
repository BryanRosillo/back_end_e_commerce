package com.ecommerce.backend.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.backend.dao.ProductoDAO;
import com.ecommerce.backend.entidades.Producto;

@Service
public class ServicioProducto {

    private final ProductoDAO productoDao;

    @Autowired
    public ServicioProducto(ProductoDAO productoDao) {
        this.productoDao = productoDao;
    }

    public Producto buscarProductoId(Long id) throws Exception{
        return productoDao.findById(id)
                            .orElseThrow(() -> new Exception("Producto no encontrado"));
    }

    public Producto guardaProducto(Producto producto){
        return this.productoDao.save(producto);
    }

    
    
}
