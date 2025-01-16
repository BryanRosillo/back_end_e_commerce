package com.ecommerce.backend.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ecommerce.backend.entidades.Producto;
import com.ecommerce.backend.servicios.ServicioProducto;

@RestController
@RequestMapping(path="/productos", produces="application/json")
public class ControladorProducto {
	
	private final ServicioProducto servicioProducto;

    @Autowired
	public ControladorProducto(ServicioProducto servicioProducto) {
        this.servicioProducto = servicioProducto;
    }

    @PostMapping("/upload/{id}")
    public ResponseEntity<String> subirImagenProducto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            Producto producto = this.servicioProducto.buscarProductoId(id);
            producto.setImagen(file.getBytes());
            this.servicioProducto.guardaProducto(producto);
            return ResponseEntity.ok("Imagen subida exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al subir la imagen");
        }
    }
	
    @GetMapping("/imagen/{id}")
    public ResponseEntity<byte[]> devolverImagenProducto(@PathVariable Long id) {
        try{
            Producto producto = this.servicioProducto.buscarProductoId(id);
            return ResponseEntity.ok(producto.getImagen());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
	
	

}
