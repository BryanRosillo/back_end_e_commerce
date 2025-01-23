package com.ecommerce.backend.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ecommerce.backend.dao.UsuarioDAO;
import com.ecommerce.backend.entidades.Usuario;
import com.ecommerce.backend.entidades.UsuarioDTO;
import com.ecommerce.backend.excepciones.ExcepcionUsuario;

/**
 * Servicio encargado de gestionar las operaciones relacionadas con los usuarios.
 * Este servicio permite validar usuarios, registrar nuevos usuarios, actualizar contraseñas,
 * y obtener información del usuario autenticado.
 */
@Service
public class ServicioUsuario {
	
	private final UsuarioDAO usuarioDao;
	private final PasswordEncoder passwordEncoder;
	private final ServicioJwt servicioJwt;
	
	/**
	 * Constructor que inyecta las dependencias necesarias para el servicio de usuarios.
	 * 
	 * @param usuarioDao El DAO encargado de las operaciones sobre los usuarios.
	 * @param passwordEncoder El codificador de contraseñas utilizado para cifrar las contraseñas.
	 * @param servicioJwt El servicio encargado de generar tokens JWT.
	 */	
	@Autowired
	public ServicioUsuario(UsuarioDAO usuarioDao, PasswordEncoder passwordEncoder, ServicioJwt servicioJwt) {
		this.usuarioDao = usuarioDao;
		this.passwordEncoder = passwordEncoder;
		this.servicioJwt = servicioJwt;
	}
	
	/**
	 * Busca un usuario por su nombre de usuario.
	 * 
	 * @param username El nombre de usuario a buscar.
	 * @return El usuario encontrado.
	 * @throws Exception Si no se encuentra el usuario.
	 */	
	@Transactional(readOnly=true)
	public Usuario buscarUsuarioPorUsername(String username) throws Exception {
		return this.usuarioDao.findByUsername(username)
				.orElseThrow(() -> new ExcepcionUsuario("Usuario no encontrado") );
	}
	
	/**
	 * Busca un usuario por su ID.
	 * 
	 * @param id El ID del usuario a buscar.
	 * @return El usuario encontrado.
	 * @throws Exception Si no se encuentra el usuario.
	 */	
	@Transactional(readOnly=true)
	public Usuario buscarUsuarioPorId(Long id) throws Exception {
		return this.usuarioDao.findById(id).orElseThrow(() -> new ExcepcionUsuario("Usuario no encontrado"));
	}

	/**
	 * Valida las credenciales de un usuario, verificando el nombre de usuario y la contraseña.
	 * 
	 * @param username El nombre de usuario a validar.
	 * @param password La contraseña del usuario a validar.
	 * @return El token JWT generado si las credenciales son válidas.
	 * @throws Exception Si las credenciales son incorrectas.
	 */	
	@Transactional
	public String validarUsuario(String username, String password) throws Exception{
		Usuario usuario = buscarUsuarioPorUsername(username);
		if(!passwordEncoder.matches(password, usuario.getPassword())) {
			throw new ExcepcionUsuario("Credenciales incorrectas");
		}
		return this.servicioJwt.generarToken(usuario.getId_usuario().toString());
	}

	/**
	 * Registra un nuevo usuario, cifrando su contraseña y su pregunta de seguridad.
	 * 
	 * @param usuario El usuario a registrar.
	 * @return El usuario registrado.
	 */	
	public Usuario registrarUsuario(Usuario usuario){
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		usuario.setPreguntaSeguridad(passwordEncoder.encode(usuario.getPreguntaSeguridad()));
		return this.usuarioDao.save(usuario);
	}

	/**
	 * Actualiza la contraseña de un usuario.
	 * 
	 * @param usuarioActualizar El objeto {@link UsuarioDTO} que contiene los nuevos datos de la contraseña.
	 * @throws Exception Si las credenciales no son correctas o si ocurre un error durante el proceso.
	 */	
	public void actualizarContrasenia(UsuarioDTO usuarioActualizar) throws Exception{
		Usuario usuario = buscarUsuarioPorUsername(usuarioActualizar.getUsername());
		if(!this.passwordEncoder.matches(usuarioActualizar.getPreguntaSeguridad(), usuario.getPreguntaSeguridad())){
			throw new ExcepcionUsuario("Credenciales incorrectas");
		}
		usuario.setPassword(this.passwordEncoder.encode(usuarioActualizar.getNuevaContrasena()));
		this.usuarioDao.save(usuario);
	}
	
	/**
	 * Devuelve el nombre de usuario del usuario autenticado.
	 * 
	 * @return El nombre de usuario del usuario autenticado.
	 * @throws ExcepcionUsuario Si no se puede obtener el nombre de usuario del usuario autenticado.
	 */	
	public static String devolverUsernameAutenticado() throws ExcepcionUsuario {
		Authentication autenticacion = SecurityContextHolder.getContext().getAuthentication(); 
		if(autenticacion != null && autenticacion.isAuthenticated()) {
			String username = (String) autenticacion.getPrincipal();
			return username;
		}else {
			throw new ExcepcionUsuario("No se puede obtener el username del usuario.");
		}
	}
}
