package com.co.prueba.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.co.prueba.datatransfer.Respuesta;
import com.co.prueba.datatransfer.UsuarioDTO;
import com.co.prueba.exception.ControlException;
import com.co.prueba.service.ErrorService;
import com.co.prueba.service.UsuarioService;

import fj.data.Either;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

	@InjectMocks
	private UsuarioController usuarioController;

	@Mock
	private UsuarioService usuarioService;

	@Mock
	private ErrorService errorService;

	@Test
	void crearUsuarioExitoso() {

		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setNombre("prueba");

		Respuesta respuesta = new Respuesta("", "", HttpStatus.OK);

		Either<Exception, Respuesta> resultEither = Either.right(respuesta);
		when(usuarioService.crearUsuario(anyString())).thenReturn(resultEither);

		ResponseEntity<?> res = usuarioController.crearUsuario(usuarioDTO);

		assertEquals(HttpStatus.OK, res.getStatusCode());
	}

	@Test
	void crearUsuarioError() {

		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setNombre("prueba");

		Either<Exception, Respuesta> resultEither = Either.left(new ControlException("", HttpStatus.BAD_REQUEST));

		Respuesta error = new Respuesta("", "", HttpStatus.BAD_REQUEST);

		when(errorService.getError(any())).thenReturn(error);
		when(usuarioService.crearUsuario(anyString())).thenReturn(resultEither);

		ResponseEntity<?> res = usuarioController.crearUsuario(usuarioDTO);

		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
	}

	@Test
	void listarUsuarioExitoso() {

		List<UsuarioDTO> listaUsuario = new ArrayList<>();

		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setNombre("prueba");
		listaUsuario.add(usuarioDTO);

		Either<Exception, List<UsuarioDTO>> resultEither = Either.right(listaUsuario);
		when(usuarioService.listarUsuario()).thenReturn(resultEither);

		ResponseEntity<?> res = usuarioController.listarUsuario();

		assertEquals(HttpStatus.OK, res.getStatusCode());
	}

	@Test
	void listarUsuarioError() {

		Either<Exception, List<UsuarioDTO>> resultEither = Either
				.left(new ControlException("", HttpStatus.BAD_REQUEST));

		Respuesta error = new Respuesta("", "", HttpStatus.BAD_REQUEST);

		when(errorService.getError(any())).thenReturn(error);

		when(usuarioService.listarUsuario()).thenReturn(resultEither);

		ResponseEntity<?> res = usuarioController.listarUsuario();

		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
	}

}
