package com.ecommerce.backend.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.backend.dao.UsuarioDAO;
import com.ecommerce.backend.entidades.Usuario;

@Service
public class ServicioUsuario {
	
	private final UsuarioDAO usuarioDao;
	
	@Autowired
	public ServicioUsuario(UsuarioDAO usuarioDao) {
		this.usuarioDao = usuarioDao;
	}
	
	@Transactional
	public Usuario buscarUsuarioPorUsername(String username) throws Exception {
		return this.usuarioDao.findByUsername(username)
				.orElseThrow(() -> new Exception("Usuario no encontrado") );
	}

}
