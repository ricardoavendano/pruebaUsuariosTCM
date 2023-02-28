package com.co.prueba.service.imp;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.co.prueba.datatransfer.Respuesta;
import com.co.prueba.datatransfer.TareaDTO;
import com.co.prueba.datatransfer.UsuarioDTO;
import com.co.prueba.domain.Tarea;
import com.co.prueba.domain.Usuario;
import com.co.prueba.repository.TareaRepository;
import com.co.prueba.service.ErrorService;
import com.co.prueba.service.impl.TareaServiceImp;

import fj.data.Either;

@ExtendWith(MockitoExtension.class)
class TareaServiceImpTest {

	@InjectMocks
	private TareaServiceImp tareaServiceImp;

	@Mock
	private TareaRepository tareaRepository;

	@Test
	void crearTareaExitoso() {

		TareaDTO tareaDTO = new TareaDTO();
		tareaDTO.setIdTarea(Long.valueOf(0));
		tareaDTO.setDiasRetraso(Long.valueOf(0));
		tareaDTO.setEstado("1");
		tareaDTO.setFechaEjecucion("2023-02-20");
		tareaDTO.setFechaCierre("2023-02-28");

		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setIdUsuario(Long.valueOf(0));
		usuarioDTO.setNombre("prueba");
		tareaDTO.setIdUsuario(usuarioDTO);

		when(tareaRepository.save(any())).thenReturn(null);

		Either<Exception, Respuesta> res = tareaServiceImp.crearTarea(tareaDTO);

		assertTrue(res.isRight());
	}

	@Test
	void editarTareaExitoso() throws ParseException {

		TareaDTO tareaDTO = new TareaDTO();
		tareaDTO.setIdTarea(Long.valueOf(1));
		tareaDTO.setDiasRetraso(Long.valueOf(0));
		tareaDTO.setEstado("1");
		tareaDTO.setFechaEjecucion("2023-02-20");
		tareaDTO.setFechaCierre("2023-02-28");

		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setIdUsuario(Long.valueOf(0));
		usuarioDTO.setNombre("prueba nuevo");
		tareaDTO.setIdUsuario(usuarioDTO);

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now));

		Tarea tarea = new Tarea();
		tarea.setIdTarea(Long.valueOf(1));
		tarea.setEstado(Long.valueOf(1));
		tarea.setFechaEjecucion(new SimpleDateFormat("yyyy-MM-dd").parse(dtf.format(now)));
		tarea.setFechaCierre(new SimpleDateFormat("yyyy-MM-dd").parse(dtf.format(now)));

		Usuario usuario = new Usuario();
		usuario.setIdUsuario(Long.valueOf(1));
		usuario.setNombre("prueba");
		tarea.setIdUsuariopk(usuario);

		when(tareaRepository.findById(any())).thenReturn(Optional.of(tarea));
		when(tareaRepository.save(any())).thenReturn(null);

		Either<Exception, Respuesta> res = tareaServiceImp.crearTarea(tareaDTO);

		assertTrue(res.isRight());
	}

	@Test
	void editarTareaError() throws ParseException {

		TareaDTO tareaDTO = new TareaDTO();
		tareaDTO.setIdTarea(Long.valueOf(1));
		tareaDTO.setDiasRetraso(Long.valueOf(0));
		tareaDTO.setEstado("1");
		tareaDTO.setFechaEjecucion("2023-02-20");
		tareaDTO.setFechaCierre("2023-02-28");

		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setIdUsuario(Long.valueOf(0));
		usuarioDTO.setNombre("prueba nuevo");
		tareaDTO.setIdUsuario(usuarioDTO);

		when(tareaRepository.findById(any())).thenReturn(Optional.empty());

		Either<Exception, Respuesta> res = tareaServiceImp.crearTarea(tareaDTO);

		ErrorService errorService = new ErrorService();
		Respuesta error = errorService.getError(res.left().value());

		assertEquals(HttpStatus.NO_CONTENT, error.getStatus());
		assertTrue(res.isLeft());
	}

	@Test
	void errorAlGuardarEnBD() throws ParseException {

		TareaDTO tareaDTO = new TareaDTO();
		tareaDTO.setIdTarea(Long.valueOf(0));
		tareaDTO.setDiasRetraso(Long.valueOf(0));
		tareaDTO.setEstado("1");
		tareaDTO.setFechaEjecucion("2023-02-20");
		tareaDTO.setFechaCierre("2023-02-28");

		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setIdUsuario(Long.valueOf(0));
		usuarioDTO.setNombre("prueba");
		tareaDTO.setIdUsuario(usuarioDTO);

		doThrow(new RuntimeException()).when(tareaRepository).save(any());

		Either<Exception, Respuesta> res = tareaServiceImp.crearTarea(tareaDTO);

		ErrorService errorService = new ErrorService();
		Respuesta error = errorService.getError(res.left().value());

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, error.getStatus());
		assertTrue(res.isLeft());
	}

	@Test
	void eliminarTareaExitoso() throws ParseException {

		TareaDTO tareaDTO = new TareaDTO();
		tareaDTO.setIdTarea(Long.valueOf(1));
		tareaDTO.setDiasRetraso(Long.valueOf(0));
		tareaDTO.setEstado("1");
		tareaDTO.setFechaEjecucion("2023-02-20");
		tareaDTO.setFechaCierre("2023-02-28");

		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setIdUsuario(Long.valueOf(0));
		usuarioDTO.setNombre("prueba nuevo");
		tareaDTO.setIdUsuario(usuarioDTO);

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now));

		Tarea tarea = new Tarea();
		tarea.setIdTarea(Long.valueOf(1));
		tarea.setEstado(Long.valueOf(1));
		tarea.setFechaEjecucion(new SimpleDateFormat("yyyy-MM-dd").parse(dtf.format(now)));
		tarea.setFechaCierre(new SimpleDateFormat("yyyy-MM-dd").parse(dtf.format(now)));

		Usuario usuario = new Usuario();
		usuario.setIdUsuario(Long.valueOf(1));
		usuario.setNombre("prueba");
		tarea.setIdUsuariopk(usuario);

		when(tareaRepository.findById(any())).thenReturn(Optional.of(tarea));
		doNothing().when(tareaRepository).delete(any());

		Either<Exception, Respuesta> res = tareaServiceImp.eliminarTarea(Long.valueOf(0));

		assertTrue(res.isRight());
	}

	@Test
	void eliminarTareaError() throws ParseException {

		when(tareaRepository.findById(any())).thenReturn(Optional.empty());

		Either<Exception, Respuesta> res = tareaServiceImp.eliminarTarea(Long.valueOf(0));

		ErrorService errorService = new ErrorService();
		Respuesta error = errorService.getError(res.left().value());

		assertEquals(HttpStatus.NO_CONTENT, error.getStatus());
		assertTrue(res.isLeft());
	}

	@Test
	void eliminarTareaExcepcion() throws ParseException {

		TareaDTO tareaDTO = new TareaDTO();
		tareaDTO.setIdTarea(Long.valueOf(1));
		tareaDTO.setDiasRetraso(Long.valueOf(0));
		tareaDTO.setEstado("1");
		tareaDTO.setFechaEjecucion("2023-02-20");
		tareaDTO.setFechaCierre("2023-02-28");

		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setIdUsuario(Long.valueOf(0));
		usuarioDTO.setNombre("prueba nuevo");
		tareaDTO.setIdUsuario(usuarioDTO);

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now));

		Tarea tarea = new Tarea();
		tarea.setIdTarea(Long.valueOf(1));
		tarea.setEstado(Long.valueOf(1));
		tarea.setFechaEjecucion(new SimpleDateFormat("yyyy-MM-dd").parse(dtf.format(now)));
		tarea.setFechaCierre(new SimpleDateFormat("yyyy-MM-dd").parse(dtf.format(now)));

		Usuario usuario = new Usuario();
		usuario.setIdUsuario(Long.valueOf(1));
		usuario.setNombre("prueba");
		tarea.setIdUsuariopk(usuario);

		when(tareaRepository.findById(any())).thenReturn(Optional.of(tarea));
		doThrow(new RuntimeException()).when(tareaRepository).delete(any());

		Either<Exception, Respuesta> res = tareaServiceImp.eliminarTarea(Long.valueOf(0));

		ErrorService errorService = new ErrorService();
		Respuesta error = errorService.getError(res.left().value());

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, error.getStatus());
		assertTrue(res.isLeft());
	}

	@Test
	void listarTodasTareasExito() throws ParseException {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now));

		Usuario usuario = new Usuario();
		usuario.setIdUsuario(Long.valueOf(0));
		usuario.setNombre("prueba");

		List<Tarea> tareaList = new ArrayList<>();
		Tarea tarea1 = new Tarea();
		tarea1.setIdTarea(Long.valueOf(1));
		tarea1.setEstado(Long.valueOf(1));
		tarea1.setFechaEjecucion(new SimpleDateFormat("yyyy-MM-dd").parse(dtf.format(now)));
		tarea1.setFechaCierre(new SimpleDateFormat("yyyy-MM-dd").parse(dtf.format(now)));
		tarea1.setIdUsuariopk(usuario);

		Tarea tarea2 = new Tarea();
		tarea2.setIdTarea(Long.valueOf(2));
		tarea2.setEstado(Long.valueOf(2));
		tarea2.setFechaEjecucion(new SimpleDateFormat("yyyy-MM-dd").parse(dtf.format(now)));
		tarea2.setFechaCierre(null);
		tarea2.setIdUsuariopk(usuario);

		tareaList.add(tarea1);
		tareaList.add(tarea2);

		when(tareaRepository.findAll()).thenReturn(tareaList);

		Either<Exception, List<TareaDTO>> res = tareaServiceImp.listarTodasTareas();

		assertTrue(res.isRight());
	}

	@Test
	void listarTodasTareasError() throws ParseException {

		List<Tarea> tareaList = new ArrayList<>();

		when(tareaRepository.findAll()).thenReturn(tareaList);

		Either<Exception, List<TareaDTO>> res = tareaServiceImp.listarTodasTareas();

		ErrorService errorService = new ErrorService();
		Respuesta error = errorService.getError(res.left().value());

		assertEquals(HttpStatus.NO_CONTENT, error.getStatus());
		assertTrue(res.isLeft());
	}

	@Test
	void listarTodasTareasExcepcion() throws ParseException {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now));

		List<Tarea> tareaList = new ArrayList<>();
		Tarea tarea1 = new Tarea();
		tarea1.setIdTarea(Long.valueOf(1));
		tarea1.setEstado(Long.valueOf(1));
		tarea1.setFechaEjecucion(new SimpleDateFormat("yyyy-MM-dd").parse(dtf.format(now)));
		tarea1.setFechaCierre(new SimpleDateFormat("yyyy-MM-dd").parse(dtf.format(now)));

		Tarea tarea2 = new Tarea();
		tarea2.setIdTarea(Long.valueOf(2));
		tarea2.setEstado(Long.valueOf(2));
		tarea2.setFechaEjecucion(new SimpleDateFormat("yyyy-MM-dd").parse(dtf.format(now)));
		tarea2.setFechaCierre(null);

		tareaList.add(tarea1);
		tareaList.add(tarea2);

		when(tareaRepository.findAll()).thenReturn(tareaList);

		Either<Exception, List<TareaDTO>> res = tareaServiceImp.listarTodasTareas();

		ErrorService errorService = new ErrorService();
		Respuesta error = errorService.getError(res.left().value());

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, error.getStatus());
		assertTrue(res.isLeft());
	}

	@Test
	void listarTodasTareasFechaExito() throws ParseException {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now));

		Usuario usuario = new Usuario();
		usuario.setIdUsuario(Long.valueOf(0));
		usuario.setNombre("prueba");

		List<Tarea> tareaList = new ArrayList<>();
		Tarea tarea1 = new Tarea();
		tarea1.setIdTarea(Long.valueOf(1));
		tarea1.setEstado(Long.valueOf(1));
		tarea1.setFechaEjecucion(new SimpleDateFormat("yyyy-MM-dd").parse(dtf.format(now)));
		tarea1.setFechaCierre(new SimpleDateFormat("yyyy-MM-dd").parse(dtf.format(now)));
		tarea1.setIdUsuariopk(usuario);

		Tarea tarea2 = new Tarea();
		tarea2.setIdTarea(Long.valueOf(2));
		tarea2.setEstado(Long.valueOf(2));
		tarea2.setFechaEjecucion(new SimpleDateFormat("yyyy-MM-dd").parse(dtf.format(now)));
		tarea2.setFechaCierre(null);
		tarea2.setIdUsuariopk(usuario);

		tareaList.add(tarea1);
		tareaList.add(tarea2);

		when(tareaRepository.findAllTareasFecha(any())).thenReturn(tareaList);

		Either<Exception, List<TareaDTO>> res = tareaServiceImp
				.listarTareasUsuarioFecha(new SimpleDateFormat("yyyy-MM-dd").parse(dtf.format(now)));

		assertTrue(res.isRight());
	}

	@Test
	void listarTodasTareasFechaError() throws ParseException {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now));

		List<Tarea> tareaList = new ArrayList<>();

		when(tareaRepository.findAllTareasFecha(any())).thenReturn(tareaList);

		Either<Exception, List<TareaDTO>> res = tareaServiceImp
				.listarTareasUsuarioFecha(new SimpleDateFormat("yyyy-MM-dd").parse(dtf.format(now)));

		ErrorService errorService = new ErrorService();
		Respuesta error = errorService.getError(res.left().value());

		assertEquals(HttpStatus.NO_CONTENT, error.getStatus());
		assertTrue(res.isLeft());
	}

	@Test
	void listarTodasTareasFechaExcepcion() throws ParseException {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now));

		List<Tarea> tareaList = new ArrayList<>();
		Tarea tarea1 = new Tarea();
		tarea1.setIdTarea(Long.valueOf(1));
		tarea1.setEstado(Long.valueOf(1));
		tarea1.setFechaEjecucion(new SimpleDateFormat("yyyy-MM-dd").parse(dtf.format(now)));
		tarea1.setFechaCierre(new SimpleDateFormat("yyyy-MM-dd").parse(dtf.format(now)));

		Tarea tarea2 = new Tarea();
		tarea2.setIdTarea(Long.valueOf(2));
		tarea2.setEstado(Long.valueOf(2));
		tarea2.setFechaEjecucion(new SimpleDateFormat("yyyy-MM-dd").parse(dtf.format(now)));
		tarea2.setFechaCierre(null);

		tareaList.add(tarea1);
		tareaList.add(tarea2);

		when(tareaRepository.findAllTareasFecha(any())).thenReturn(tareaList);

		Either<Exception, List<TareaDTO>> res = tareaServiceImp
				.listarTareasUsuarioFecha(new SimpleDateFormat("yyyy-MM-dd").parse(dtf.format(now)));

		ErrorService errorService = new ErrorService();
		Respuesta error = errorService.getError(res.left().value());

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, error.getStatus());
		assertTrue(res.isLeft());
	}

}
