package com.co.prueba.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.co.prueba.datatransfer.Respuesta;
import com.co.prueba.exception.ControlException;

@ExtendWith(MockitoExtension.class)
class ErrorServiceTest {

	@InjectMocks
	private ErrorService errorService;

	@Test
	void ControlException() {

		Respuesta error = errorService.getError(new ControlException("", HttpStatus.BAD_REQUEST));
		assertEquals(HttpStatus.BAD_REQUEST, error.getStatus());
	}

	@Test
	void errorDesconocido() {
		Respuesta error = errorService.getError(new Exception());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, error.getStatus());
	}
}
