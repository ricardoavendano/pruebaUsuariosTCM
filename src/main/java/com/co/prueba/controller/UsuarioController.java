package com.co.prueba.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.co.prueba.datatransfer.Respuesta;
import com.co.prueba.datatransfer.UsuarioDTO;
import com.co.prueba.service.ErrorService;
import com.co.prueba.service.UsuarioService;

import fj.data.Either;

@EnableAutoConfiguration
@CrossOrigin(origins = "*")
@RequestMapping
@RestController
@Controller
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private ErrorService errorService;

	@PostMapping(value = "/crearUsuario")
	public ResponseEntity<?> crearUsuario(@RequestBody UsuarioDTO usuarioDTO) {

		Either<Exception, Respuesta> resultEither = usuarioService.crearUsuario(usuarioDTO.getNombre());

		if (resultEither.isRight()) {
			return new ResponseEntity<>(resultEither.right().value(), HttpStatus.OK);
		}

		Respuesta error = errorService.getError(resultEither.left().value());

		return new ResponseEntity<>(error, error.getStatus());
	}

	@GetMapping(value = "/listarUsuario")
	public ResponseEntity<?> listarUsuario() {

		Either<Exception, List<UsuarioDTO>> resultEither = usuarioService.listarUsuario();

		if (resultEither.isRight()) {
			return new ResponseEntity<>(resultEither.right().value(), HttpStatus.OK);
		}

		Respuesta error = errorService.getError(resultEither.left().value());

		return new ResponseEntity<>(error, error.getStatus());
	}

}
