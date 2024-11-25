package com.ecommerce.backend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.backend.dao.ProductoDAO;
import com.ecommerce.backend.entidades.Producto;

@RestController
@RequestMapping(path="/productos", produces="application/json")
public class ProductoREST {
	
	@Autowired
	private ProductoDAO productoDao;
	
	
	@PostMapping("/upload/{id}")
    public ResponseEntity<String> subirImagenProducto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            Producto producto = productoDao.findById(id)
                                                   .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            producto.setImagen(file.getBytes());
            productoDao.save(producto);
            return ResponseEntity.ok("Imagen subida exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al subir la imagen: " + e.getMessage());
        }
    }
	
    @GetMapping("/imagen/{id}")
    public ResponseEntity<byte[]> devolverImagenProducto(@PathVariable Long id) {
        Producto producto = productoDao.findById(id)
                                               .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        return ResponseEntity.ok(producto.getImagen());
    }
	
	

}
