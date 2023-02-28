package com.co.prueba.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.co.prueba.datatransfer.Respuesta;
import com.co.prueba.datatransfer.UsuarioDTO;
import com.co.prueba.domain.Usuario;
import com.co.prueba.exception.ControlException;
import com.co.prueba.repository.UsuarioRepository;
import com.co.prueba.service.UsuarioService;

import fj.data.Either;

@Service
public class UsuarioServiceImp implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public Either<Exception, Respuesta> crearUsuario(String nombre) {
		try {
			Usuario usuario = new Usuario();
			usuario.setIdUsuario(Long.valueOf(0));
			usuario.setNombre(nombre);
			usuarioRepository.save(usuario);
			Respuesta respuesta = new Respuesta("200", "Usuario creado con exito", HttpStatus.OK);
			return Either.right(respuesta);
		} catch (Exception e) {
			return Either.left(new ControlException("No es posible crear usuario", HttpStatus.INTERNAL_SERVER_ERROR));
		}
	}

	@Override
	public Either<Exception, List<UsuarioDTO>> listarUsuario() {
		try {
			List<Usuario> usuarios = (List<Usuario>) usuarioRepository.findAll();
			if (!usuarios.isEmpty()) {
				List<UsuarioDTO> listaUsuarios = new ArrayList<>();
				for (Usuario Usuario : usuarios) {
					UsuarioDTO usuarioDTO = new UsuarioDTO();
					usuarioDTO.setIdUsuario(Usuario.getIdUsuario());
					usuarioDTO.setNombre(Usuario.getNombre());
					listaUsuarios.add(usuarioDTO);
				}
				return Either.right(listaUsuarios);
			} else {
				return Either.left(new ControlException("No se encontraron resultados", HttpStatus.NO_CONTENT));
			}

		} catch (Exception e) {
			return Either.left(new ControlException("Error consultando Usuarios", HttpStatus.INTERNAL_SERVER_ERROR));
		}
	}

}
