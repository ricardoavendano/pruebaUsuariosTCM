package com.co.prueba.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.co.prueba.datatransfer.Respuesta;
import com.co.prueba.datatransfer.UsuarioDTO;

import fj.data.Either;

@Service
public interface UsuarioService {

	public Either<Exception, Respuesta> crearUsuario(String nombre);

	public Either<Exception, List<UsuarioDTO>> listarUsuario();

}
