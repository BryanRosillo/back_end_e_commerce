package com.ecommerce.backend.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ecommerce.backend.entidades.Producto;
import com.ecommerce.backend.excepciones.ExcepcionProducto;
import com.ecommerce.backend.servicios.ServicioProducto;

import jakarta.validation.Valid;

/**
 * Controlador encargado de gestionar los productos en la plataforma de e-commerce.
 * Permite crear, editar, eliminar productos, así como gestionar sus imágenes y obtener productos de un usuario.
 */
@RestController
@RequestMapping(path="/productos", produces="application/json")
public class ControladorProducto {
	
	private final ServicioProducto servicioProducto;

    /**
     * Constructor que inyecta el servicio que maneja la lógica de productos.
     * 
     * @param servicioProducto El servicio que maneja la lógica de los productos.
     */    
    @Autowired
	public ControladorProducto(ServicioProducto servicioProducto) {
        this.servicioProducto = servicioProducto;
    }
    
    /**
     * Método que crea un nuevo producto.
     * 
     * @param producto El producto a ser creado, proporcionado en formato JSON.
     * @return Una respuesta indicando si el producto fue creado con éxito o si hubo un error.
     */    
    @PostMapping
    public ResponseEntity<Object> crearProducto(@RequestBody @Valid Producto producto){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(this.servicioProducto.crearProducto(producto));
        }catch(Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Método que permite subir una imagen para un producto existente.
     * 
     * @param id El ID del producto al cual se le asociará la imagen.
     * @param file El archivo de la imagen que será subida.
     * @return Una respuesta indicando si la imagen fue subida con éxito o si hubo un error.
     */
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
	
    /**
     * Método que devuelve la imagen de un producto dado su ID.
     * 
     * @param id El ID del producto cuya imagen se desea obtener.
     * @return La imagen del producto en formato de bytes.
     */    
    @GetMapping("/imagen/{id}")
    public ResponseEntity<byte[]> devolverImagenProducto(@PathVariable Long id) {
        try{
            Producto producto = this.servicioProducto.buscarProductoId(id);
            return ResponseEntity.ok(producto.getImagen());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
	
    /**
     * Método que obtiene todos los productos asociados al usuario autenticado.
     * 
     * @return Una lista de productos asociados al usuario. Si no tiene productos, se devuelve una respuesta con código 204.
     */    
    @GetMapping("/usuario")
    public ResponseEntity<List<Producto>> obtenerProductosPorUsuario() {
        List<Producto> productos = servicioProducto.obtenerProductosPorUsuario();
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build(); // Si no hay productos, responde con código 204
        }
        return ResponseEntity.ok(productos); // Devuelve la lista de productos en formato JSON
    }

    /**
     * Método que edita un producto existente.
     * 
     * @param id El ID del producto a ser editado.
     * @param productoEditado El objeto que contiene los nuevos datos para el producto.
     * @return El producto actualizado en formato JSON.
     */    
    @PatchMapping("/editar/{id}")
    public ResponseEntity<Object> editarProducto(@PathVariable Long id, @RequestBody Producto productoEditado) {
        try {
            Producto productoActualizado = servicioProducto.editarProducto(id, productoEditado);
            return ResponseEntity.ok(productoActualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el producto: " + e.getMessage());
        }
    }
    
    /**
     * Método que busca un producto por su ID.
     * 
     * @param id El ID del producto a buscar.
     * @return El producto encontrado o una respuesta con código 404 si no se encuentra.
     */    
    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarProductoId(@PathVariable Long id){
        try {
            return ResponseEntity.ok(this.servicioProducto.buscarProductoId(id));
        }catch(ExcepcionProducto e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
	
    /**
     * Método que devuelve todos los productos.
     * 
     * @return Una lista de todos los productos disponibles.
     */    
    @GetMapping
    public ResponseEntity<Object> devolverProductos(){
        return ResponseEntity.ok(this.servicioProducto.devolverProductos());
    }
    

}
