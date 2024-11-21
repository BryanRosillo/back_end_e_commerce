package com.ecommerce.backend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.dao.ProductoDAO;
import com.ecommerce.backend.entidades.Producto;

@RestController
@RequestMapping(path="/productos", produces="application/json")
public class ProductoREST {
	
	@Autowired
	private ProductoDAO productoDao;
	
	
	@PostMapping(consumes="application/json")
	public Producto agregarProducto(@RequestBody Producto producto) {
		return productoDao.save(producto);
	}
	
	
	

}
