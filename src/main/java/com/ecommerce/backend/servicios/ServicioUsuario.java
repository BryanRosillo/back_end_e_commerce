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

@Service
public class ServicioUsuario {
	
	private final UsuarioDAO usuarioDao;
	private final PasswordEncoder passwordEncoder;
	private final ServicioJwt servicioJwt;
	
	@Autowired
	public ServicioUsuario(UsuarioDAO usuarioDao, PasswordEncoder passwordEncoder, ServicioJwt servicioJwt) {
		this.usuarioDao = usuarioDao;
		this.passwordEncoder = passwordEncoder;
		this.servicioJwt = servicioJwt;
	}
	
	@Transactional(readOnly=true)
	public Usuario buscarUsuarioPorUsername(String username) throws Exception {
		return this.usuarioDao.findByUsername(username)
				.orElseThrow(() -> new ExcepcionUsuario("Usuario no encontrado") );
	}
	
	@Transactional(readOnly=true)
	public Usuario buscarUsuarioPorId(Long id) throws Exception {
		return this.usuarioDao.findById(id).orElseThrow(() -> new ExcepcionUsuario("Usuario no encontrado"));
	}

	@Transactional
	public String validarUsuario(String username, String password) throws Exception{
		Usuario usuario = buscarUsuarioPorUsername(username);
		if(!passwordEncoder.matches(password, usuario.getPassword())) {
			throw new ExcepcionUsuario("Credenciales incorrectas");
		}
		return this.servicioJwt.generarToken(usuario.getId_usuario().toString());
	}

	public Usuario registrarUsuario(Usuario usuario){
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		usuario.setPreguntaSeguridad(passwordEncoder.encode(usuario.getPreguntaSeguridad()));
		return this.usuarioDao.save(usuario);
	}

	public void actualizarContrasenia(UsuarioDTO usuarioActualizar) throws Exception{
		Usuario usuario = buscarUsuarioPorUsername(usuarioActualizar.getUsername());
		if(!this.passwordEncoder.matches(usuarioActualizar.getPreguntaSeguridad(), usuario.getPreguntaSeguridad())){
			throw new ExcepcionUsuario("Credenciales incorrectas");
		}
		usuario.setPassword(this.passwordEncoder.encode(usuarioActualizar.getNuevaContrasena()));
		this.usuarioDao.save(usuario);
	}
	
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
