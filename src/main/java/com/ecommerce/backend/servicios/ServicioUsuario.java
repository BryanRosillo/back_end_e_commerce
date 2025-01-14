package com.ecommerce.backend.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.backend.dao.UsuarioDAO;
import com.ecommerce.backend.entidades.Usuario;
import com.ecommerce.backend.entidades.UsuarioDTO;

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
	
	@Transactional
	public Usuario buscarUsuarioPorUsername(String username) throws Exception {
		return this.usuarioDao.findByUsername(username)
				.orElseThrow(() -> new Exception("Usuario no encontrado") );
	}

	public String validarUsuario(String username) throws Exception{
		Usuario usuario = this.buscarUsuarioPorUsername(username);
		if(!passwordEncoder.matches(usuario.getPassword(), usuario.getPassword())) {
			throw new Exception("Credenciales incorrectas");
		}
		return this.servicioJwt.generarToken(usuario.getId_usuario().toString());
	}

	public Usuario guardarUsuario(Usuario usuario){
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		usuario.setPreguntaSeguridad(passwordEncoder.encode(usuario.getPreguntaSeguridad()));
		return this.usuarioDao.save(usuario);
	}

	public void actualizarContrasenia(UsuarioDTO usuarioActualizar) throws Exception{
		Usuario usuario = this.buscarUsuarioPorUsername(usuarioActualizar.getUsername());
		if(!this.passwordEncoder.matches(usuarioActualizar.getPreguntaSeguridad(), usuario.getPreguntaSeguridad())){
			throw new Exception("Credenciales incorrectas");
		}
		usuario.setPassword(this.passwordEncoder.encode(usuarioActualizar.getNuevaContrasena()));
		this.guardarUsuario(usuario);
	}

}
