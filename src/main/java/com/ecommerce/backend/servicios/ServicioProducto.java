package com.ecommerce.backend.servicios;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.backend.dao.ProductoDAO;
import com.ecommerce.backend.dao.UsuarioDAO;
import com.ecommerce.backend.entidades.Producto;
import com.ecommerce.backend.entidades.Usuario;
import com.ecommerce.backend.excepciones.ExcepcionProducto;

/**
 * Servicio encargado de la gestión de productos en la plataforma.
 * Este servicio permite la creación, edición, búsqueda y obtención de productos relacionados con un usuario.
 */
@Service
public class ServicioProducto {

    private final ProductoDAO productoDao;
    private final UsuarioDAO usuarioDao;
    private final ServicioUsuario servicioUsuario;

    /**
     * Constructor que inyecta las dependencias necesarias para el servicio de productos.
     * 
     * @param productoDao El DAO encargado de las operaciones sobre los productos.
     * @param usuarioDAO El DAO encargado de las operaciones sobre los usuarios.
     * @param servicioUsuario El servicio encargado de gestionar los usuarios.
     */    
    @Autowired
    public ServicioProducto(ProductoDAO productoDao, UsuarioDAO usuarioDAO, ServicioUsuario servicioUsuario) {
        this.productoDao = productoDao;
        this.usuarioDao = usuarioDAO;
        this.servicioUsuario = servicioUsuario;
    }

    /**
     * Crea un nuevo producto y lo asocia con el usuario autenticado.
     * 
     * @param producto El objeto {@link Producto} a crear.
     * @return El producto creado.
     * @throws Exception Si ocurre algún error al crear el producto.
     */    
    public Producto crearProducto(Producto producto) throws Exception{
        Usuario usuario = this.servicioUsuario.buscarUsuarioPorUsername(ServicioUsuario.devolverUsernameAutenticado());
        producto.setUsuario(usuario);
        return this.guardaProducto(producto);
    }

    /**
     * Busca un producto por su ID.
     * 
     * @param id El ID del producto a buscar.
     * @return El producto encontrado.
     * @throws ExcepcionProducto Si no se encuentra el producto.
     */    
    public Producto buscarProductoId(Long id) throws ExcepcionProducto{
        return productoDao.findById(id)
                            .orElseThrow(() -> new ExcepcionProducto("Producto no encontrado"));
    }

    /**
     * Guarda el producto en la base de datos.
     * 
     * @param producto El producto a guardar.
     * @return El producto guardado.
     */
    public Producto guardaProducto(Producto producto){
        return this.productoDao.save(producto);
    }

    /**
     * Obtiene todos los productos asociados al usuario autenticado.
     * 
     * @return Una lista de objetos {@link Producto} asociados al usuario autenticado.
     */    
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

    /**
     * Edita un producto existente, actualizando sus campos con los valores proporcionados.
     * 
     * @param id El ID del producto a editar.
     * @param productoEditado El objeto {@link Producto} con los nuevos valores.
     * @return El producto editado.
     * @throws RuntimeException Si el producto no se encuentra.
     */    
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
    
    /**
     * Método que guarda la imagen de un producto. En el proceso, se verifica el peso y extensión.
     * 
     * @param imagen - imagen del producto de tipo MultipartFile.
     * @param id - id del producto asociado.
     * @throws ExcepcionProducto si hay un problema al subir la imagen. 
     * @throws IOException 
     */
    public void guardarImagenProducto(MultipartFile imagen, Long id) throws ExcepcionProducto, IOException {
    	//Se verifica el peso de la imagen
    	long maxSize = 3 * 1024 * 1024; // 3 MB en bytes
        if (imagen.getSize() > maxSize) {
            throw new ExcepcionProducto("La imagen sobrepasa el peso máximo.");
        }
        
        // Verificar extensión de archivo
        String extension = FilenameUtils.getExtension(imagen.getOriginalFilename()).toLowerCase();
        if (!(extension.equals("png") || extension.equals("jpg") || extension.equals("jpeg"))) {
        	throw new ExcepcionProducto("Solo se permiten archivos PNG o JPG.");
        }

        // Verificar tipo MIME real 
        Tika tika = new Tika();
        String mimeType = tika.detect(imagen.getInputStream());
        if (!(mimeType.equals("image/png") || mimeType.equals("image/jpeg"))) {
        	throw new ExcepcionProducto("El archivo no es una imagen válida.");
        }
        
        //Se guarda el producto
        Producto producto = this.buscarProductoId(id);
        producto.setImagen(imagen.getBytes());
        this.guardaProducto(producto);
    }
}
