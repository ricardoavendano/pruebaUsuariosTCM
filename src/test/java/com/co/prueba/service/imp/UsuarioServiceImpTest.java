package com.co.prueba.service.imp;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.co.prueba.datatransfer.Respuesta;
import com.co.prueba.datatransfer.UsuarioDTO;
import com.co.prueba.domain.Tarea;
import com.co.prueba.domain.Usuario;
import com.co.prueba.repository.UsuarioRepository;
import com.co.prueba.service.ErrorService;
import com.co.prueba.service.impl.UsuarioServiceImp;

import fj.data.Either;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceImpTest {

	@InjectMocks
	private UsuarioServiceImp usuarioServiceImp;

	@Mock
	private UsuarioRepository usuarioRepository;

	@Test
	void crearUsuarioExitoso() {

		when(usuarioRepository.save(any())).thenReturn(null);

		Either<Exception, Respuesta> res = usuarioServiceImp.crearUsuario("prueba");

		assertTrue(res.isRight());
	}

	@Test
	void actualizarUsuarioExitoso() {

		Usuario usuario = new Usuario();
		usuario.setNombre("prueba");

		when(usuarioRepository.save(any())).thenReturn(null);

		Either<Exception, Respuesta> res = usuarioServiceImp.crearUsuario("prueba");

		assertTrue(res.isRight());
	}

	@Test
	void crearUsuarioErrorFormatoCampos() {

		doThrow(new RuntimeException()).when(usuarioRepository).save(any());

		Either<Exception, Respuesta> res = usuarioServiceImp.crearUsuario(null);

		ErrorService errorService = new ErrorService();
		Respuesta error = errorService.getError(res.left().value());

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, error.getStatus());
		assertTrue(res.isLeft());
	}

	@Test
	void listarUsuarioExitoso() throws ParseException {

		List<Usuario> usuarios = new ArrayList<>();

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now));

		Usuario usuario1 = new Usuario();
		usuario1.setIdUsuario(Long.valueOf(1));
		usuario1.setNombre("prueba 1");

		Usuario usuario2 = new Usuario();
		usuario2.setIdUsuario(Long.valueOf(2));
		usuario2.setNombre("prueba 2");

		List<Tarea> tareaList = new ArrayList<>();
		Tarea tarea = new Tarea();
		tarea.setEstado(Long.valueOf(1));
		tarea.setFechaEjecucion(new SimpleDateFormat("yyyy-MM-dd").parse(dtf.format(now)));
		tarea.setFechaCierre(new SimpleDateFormat("yyyy-MM-dd").parse(dtf.format(now)));
		tareaList.add(tarea);

		usuario1.setTareaList(tareaList);
		usuario2.setTareaList(tareaList);

		usuarios.add(usuario1);
		usuarios.add(usuario2);

		when(usuarioRepository.findAll()).thenReturn(usuarios);

		Either<Exception, List<UsuarioDTO>> res = usuarioServiceImp.listarUsuario();

		assertTrue(res.isRight());
	}

	@Test
	void listarUsuarioException() {

		doThrow(new RuntimeException()).when(usuarioRepository).findAll();

		Either<Exception, List<UsuarioDTO>> res = usuarioServiceImp.listarUsuario();

		ErrorService errorService = new ErrorService();
		Respuesta error = errorService.getError(res.left().value());

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, error.getStatus());
		assertTrue(res.isLeft());
	}

	@Test
	void listarUsuarioNoResultados() {

		List<Usuario> usuarios = new ArrayList<>();
		when(usuarioRepository.findAll()).thenReturn(usuarios);

		Either<Exception, List<UsuarioDTO>> res = usuarioServiceImp.listarUsuario();

		ErrorService errorService = new ErrorService();
		Respuesta error = errorService.getError(res.left().value());

		assertEquals(HttpStatus.NO_CONTENT, error.getStatus());
		assertTrue(res.isLeft());

	}

}
