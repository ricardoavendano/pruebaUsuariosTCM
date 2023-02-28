package com.co.prueba.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.co.prueba.datatransfer.Respuesta;
import com.co.prueba.datatransfer.TareaDTO;
import com.co.prueba.service.ErrorService;
import com.co.prueba.service.TareaService;

import fj.data.Either;

@EnableAutoConfiguration
@CrossOrigin(origins = "*")
@RequestMapping
@RestController
@Controller
public class TareaController {

	@Autowired
	private TareaService tareaService;

	@Autowired
	private ErrorService errorService;

	@PostMapping(value = "/crearTarea")
	public ResponseEntity<?> crearTarea(@RequestBody TareaDTO tareaDTO) {

		Either<Exception, Respuesta> resultEither = tareaService.crearTarea(tareaDTO);

		if (resultEither.isRight()) {
			return new ResponseEntity<>(resultEither.right().value(), HttpStatus.OK);
		}

		Respuesta error = errorService.getError(resultEither.left().value());

		return new ResponseEntity<>(error, error.getStatus());
	}

	@GetMapping(value = "/eliminarTarea/{tareadId}")
	public ResponseEntity<?> eliminarTarea(@PathVariable Long tareadId) {

		Either<Exception, Respuesta> resultEither = tareaService.eliminarTarea(tareadId);

		if (resultEither.isRight()) {
			return new ResponseEntity<>(resultEither.right().value(), HttpStatus.OK);
		}

		Respuesta error = errorService.getError(resultEither.left().value());

		return new ResponseEntity<>(error, error.getStatus());
	}

	@GetMapping(value = "/listarTodasTareas")
	public ResponseEntity<?> listarTodasTareas() {

		Either<Exception, List<TareaDTO>> resultEither = tareaService.listarTodasTareas();

		if (resultEither.isRight()) {
			return new ResponseEntity<>(resultEither.right().value(), HttpStatus.OK);
		}

		Respuesta error = errorService.getError(resultEither.left().value());

		return new ResponseEntity<>(error, error.getStatus());
	}

	@GetMapping(value = "/listarTareasUsuarioFecha")
	public ResponseEntity<?> listarTareasUsuarioFecha(
			@RequestParam("fecha") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha) {

		Either<Exception, List<TareaDTO>> resultEither = tareaService.listarTareasUsuarioFecha(fecha);

		if (resultEither.isRight()) {
			return new ResponseEntity<>(resultEither.right().value(), HttpStatus.OK);
		}

		Respuesta error = errorService.getError(resultEither.left().value());

		return new ResponseEntity<>(error, error.getStatus());
	}

}
