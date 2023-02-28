package com.co.prueba.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.co.prueba.datatransfer.Respuesta;
import com.co.prueba.exception.ControlException;

@Service
public class ErrorService {

	public Respuesta getError(Exception e) {

		if (e instanceof ControlException) {
			return new Respuesta("ControlException", ((ControlException) e).getCausaError(),
					((ControlException) e).getStatus());
		}

		Map<String, String> params = new HashMap<>();
		params.put("Exception", e.getClass() + "-" + e.getMessage());
		params.put("Time", LocalDateTime.now().toString());

		return new Respuesta("ControlException", "Unknown Respuesta", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
