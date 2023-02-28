package com.co.prueba.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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
import org.springframework.http.ResponseEntity;

import com.co.prueba.datatransfer.Respuesta;
import com.co.prueba.datatransfer.TareaDTO;
import com.co.prueba.exception.ControlException;
import com.co.prueba.service.ErrorService;
import com.co.prueba.service.TareaService;

import fj.data.Either;

@ExtendWith(MockitoExtension.class)
class TareaControllerTest {

	@InjectMocks
	private TareaController tareaController;

	@Mock
	private TareaService tareaService;

	@Mock
	private ErrorService errorService;

	@Test
	void crearTareaExitoso() {

		TareaDTO tareaDTO = new TareaDTO();
		tareaDTO.setIdTarea(Long.valueOf(0));

		Respuesta respuesta = new Respuesta("", "", HttpStatus.OK);

		Either<Exception, Respuesta> resultEither = Either.right(respuesta);
		when(tareaService.crearTarea(any())).thenReturn(resultEither);

		ResponseEntity<?> res = tareaController.crearTarea(tareaDTO);

		assertEquals(HttpStatus.OK, res.getStatusCode());
	}

	@Test
	void crearTareaError() {

		TareaDTO tareaDTO = new TareaDTO();
		tareaDTO.setIdTarea(Long.valueOf(0));

		Either<Exception, Respuesta> resultEither = Either.left(new ControlException("", HttpStatus.BAD_REQUEST));

		Respuesta error = new Respuesta("", "", HttpStatus.BAD_REQUEST);

		when(errorService.getError(any())).thenReturn(error);
		when(tareaService.crearTarea(any())).thenReturn(resultEither);

		ResponseEntity<?> res = tareaController.crearTarea(tareaDTO);

		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
	}

	@Test
	void eliminarTareaExitoso() {

		Respuesta respuesta = new Respuesta("", "", HttpStatus.OK);

		Either<Exception, Respuesta> resultEither = Either.right(respuesta);
		when(tareaService.eliminarTarea(any())).thenReturn(resultEither);

		ResponseEntity<?> res = tareaController.eliminarTarea(Long.valueOf(0));

		assertEquals(HttpStatus.OK, res.getStatusCode());
	}

	@Test
	void eliminarTareaError() {

		Either<Exception, Respuesta> resultEither = Either.left(new ControlException("", HttpStatus.BAD_REQUEST));

		Respuesta error = new Respuesta("", "", HttpStatus.BAD_REQUEST);

		when(errorService.getError(any())).thenReturn(error);
		when(tareaService.eliminarTarea(any())).thenReturn(resultEither);

		ResponseEntity<?> res = tareaController.eliminarTarea(Long.valueOf(0));

		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
	}

	@Test
	void listarTodasTareasExitoso() {

		List<TareaDTO> listaTarea = new ArrayList<>();
		TareaDTO tareaDTO = new TareaDTO();
		tareaDTO.setIdTarea(Long.valueOf(0));
		listaTarea.add(tareaDTO);

		Either<Exception, List<TareaDTO>> resultEither = Either.right(listaTarea);
		when(tareaService.listarTodasTareas()).thenReturn(resultEither);

		ResponseEntity<?> res = tareaController.listarTodasTareas();

		assertEquals(HttpStatus.OK, res.getStatusCode());
	}

	@Test
	void listarTodasTareasError() {

		List<TareaDTO> listaTarea = new ArrayList<>();
		TareaDTO tareaDTO = new TareaDTO();
		tareaDTO.setIdTarea(Long.valueOf(0));
		listaTarea.add(tareaDTO);

		Either<Exception, List<TareaDTO>> resultEither = Either.left(new ControlException("", HttpStatus.BAD_REQUEST));

		Respuesta error = new Respuesta("", "", HttpStatus.BAD_REQUEST);

		when(errorService.getError(any())).thenReturn(error);
		when(tareaService.listarTodasTareas()).thenReturn(resultEither);

		ResponseEntity<?> res = tareaController.listarTodasTareas();

		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
	}

	@Test
	void listarTodasTareasFechaExitoso() throws ParseException {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now));

		List<TareaDTO> listaTarea = new ArrayList<>();
		TareaDTO tareaDTO = new TareaDTO();
		tareaDTO.setIdTarea(Long.valueOf(0));
		listaTarea.add(tareaDTO);

		Either<Exception, List<TareaDTO>> resultEither = Either.right(listaTarea);
		when(tareaService.listarTareasUsuarioFecha(any())).thenReturn(resultEither);

		ResponseEntity<?> res = tareaController
				.listarTareasUsuarioFecha(new SimpleDateFormat("yyyy-MM-dd").parse(dtf.format(now)));

		assertEquals(HttpStatus.OK, res.getStatusCode());
	}

	@Test
	void listarTodasTareasFechaError() throws ParseException {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now));

		List<TareaDTO> listaTarea = new ArrayList<>();
		TareaDTO tareaDTO = new TareaDTO();
		tareaDTO.setIdTarea(Long.valueOf(0));
		listaTarea.add(tareaDTO);

		Either<Exception, List<TareaDTO>> resultEither = Either.left(new ControlException("", HttpStatus.BAD_REQUEST));

		Respuesta error = new Respuesta("", "", HttpStatus.BAD_REQUEST);

		when(errorService.getError(any())).thenReturn(error);
		when(tareaService.listarTareasUsuarioFecha(any())).thenReturn(resultEither);

		ResponseEntity<?> res = tareaController
				.listarTareasUsuarioFecha(new SimpleDateFormat("yyyy-MM-dd").parse(dtf.format(now)));

		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
	}
}
