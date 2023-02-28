package com.co.prueba.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.co.prueba.datatransfer.Respuesta;
import com.co.prueba.datatransfer.TareaDTO;
import com.co.prueba.datatransfer.UsuarioDTO;
import com.co.prueba.domain.Tarea;
import com.co.prueba.domain.Usuario;
import com.co.prueba.exception.ControlException;
import com.co.prueba.repository.TareaRepository;
import com.co.prueba.service.TareaService;

import fj.data.Either;

@Service
public class TareaServiceImp implements TareaService {

	private static final String DATE_FORMAT = "yyyy-MM-dd";

	@Autowired
	private TareaRepository tareaRepository;

	@Override
	public Either<Exception, Respuesta> crearTarea(TareaDTO tareaDTO) {
		try {

			Tarea tarea = new Tarea();
			if (!tareaDTO.getIdTarea().equals(Long.valueOf(0))) {
				tarea.setIdTarea(tareaDTO.getIdTarea());

				Either<Exception, Tarea> tareaBuscar = buscartarea(tareaDTO.getIdTarea());
				if (tareaBuscar.isLeft()) {
					return Either.left(new ControlException("No se encontro tarea", HttpStatus.NO_CONTENT));
				}
			} else {
				tarea.setIdTarea(Long.valueOf(0));
			}
			tarea.setEstado(Long.valueOf(tareaDTO.getEstado()));
			tarea.setFechaCierre(tareaDTO.getFechaCierre() != null
					? new SimpleDateFormat(DATE_FORMAT).parse(tareaDTO.getFechaCierre())
					: null);
			tarea.setFechaEjecucion(tareaDTO.getFechaEjecucion() != null
					? new SimpleDateFormat(DATE_FORMAT).parse(tareaDTO.getFechaEjecucion())
					: null);

			Usuario empleado = new Usuario();
			empleado.setIdUsuario(tareaDTO.getIdUsuario().getIdUsuario());

			tarea.setIdUsuariopk(empleado);
			tareaRepository.save(tarea);
			if (!tareaDTO.getIdTarea().equals(Long.valueOf(0))) {
				Respuesta respuesta = new Respuesta("200", "Tarea editada con exito", HttpStatus.OK);
				return Either.right(respuesta);
			} else {
				Respuesta respuesta = new Respuesta("200", "Tarea creada con exito", HttpStatus.OK);
				return Either.right(respuesta);
			}

		} catch (Exception e) {
			return Either.left(new ControlException("No es posible crear tarea", HttpStatus.INTERNAL_SERVER_ERROR));
		}
	}

	@Override
	public Either<Exception, Respuesta> eliminarTarea(Long tareaId) {
		try {

			Either<Exception, Tarea> tarea = buscartarea(tareaId);
			if (tarea.isRight()) {
				tareaRepository.delete(tarea.right().value());
				Respuesta respuesta = new Respuesta("200", "Tarea eliminada con exito", HttpStatus.OK);
				return Either.right(respuesta);
			} else {
				return Either.left(new ControlException("No se encontro tarea", HttpStatus.NO_CONTENT));
			}

		} catch (Exception e) {
			return Either.left(new ControlException("No es posible crear tarea", HttpStatus.INTERNAL_SERVER_ERROR));
		}
	}

	private Either<Exception, Tarea> buscartarea(Long tareaId) {
		Optional<Tarea> tarea = tareaRepository.findById(tareaId);
		if (tarea.isPresent()) {
			return Either.right(tarea.get());
		} else {
			return Either.left(null);
		}
	}

	@Override
	public Either<Exception, List<TareaDTO>> listarTodasTareas() {
		try {
			List<Tarea> tareaList = (List<Tarea>) tareaRepository.findAll();
			if (!tareaList.isEmpty()) {
				return listarTareas(tareaList);
			} else {
				return Either.left(new ControlException("No se encontraron resultados", HttpStatus.NO_CONTENT));
			}

		} catch (Exception e) {
			return Either.left(new ControlException("Error consultando tareaes", HttpStatus.INTERNAL_SERVER_ERROR));
		}
	}

	@Override
	public Either<Exception, List<TareaDTO>> listarTareasUsuarioFecha(Date fecha) {

		try {
			List<Tarea> tareaList = tareaRepository.findAllTareasFecha(fecha);
			if (!tareaList.isEmpty()) {
				return listarTareas(tareaList);
			} else {
				return Either.left(new ControlException("No se encontraron resultados", HttpStatus.NO_CONTENT));
			}

		} catch (Exception e) {
			return Either.left(new ControlException("Error consultando tareaes", HttpStatus.INTERNAL_SERVER_ERROR));
		}
	}

	private Either<Exception, List<TareaDTO>> listarTareas(List<Tarea> tareaList) {
		List<TareaDTO> listatarea = new ArrayList<>();
		for (Tarea tarea : tareaList) {
			TareaDTO tareaDTO = new TareaDTO();

			tareaDTO.setIdTarea(tarea.getIdTarea());
			tareaDTO.setEstado(tarea.getEstado().equals(Long.valueOf(1)) ? "Tarea terminada" : "Tarea pendiente");
			DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
			tareaDTO.setFechaCierre(tarea.getFechaCierre() != null ? dateFormat.format(tarea.getFechaCierre()) : null);
			tareaDTO.setFechaEjecucion(
					tarea.getFechaEjecucion() != null ? dateFormat.format(tarea.getFechaEjecucion()) : null);

			evaluarFechas(tarea, tareaDTO);

			listatarea.add(tareaDTO);

		}
		return Either.right(listatarea);
	}

	private void evaluarFechas(Tarea tarea, TareaDTO tareaDTO) {
		if (tarea.getFechaCierre() == null) {
			tareaDTO.setDiasRetraso(
					(new Date(System.currentTimeMillis()).getTime() - tarea.getFechaEjecucion().getTime()) / 86400000);
			if (tareaDTO.getDiasRetraso() < 0) {
				tareaDTO.setDiasRetraso(Long.valueOf(0));
			}
		} else {
			tareaDTO.setDiasRetraso(Long.valueOf(0));
		}

		UsuarioDTO empleadoDTO = new UsuarioDTO();
		empleadoDTO.setIdUsuario(tarea.getIdUsuariopk().getIdUsuario());
		empleadoDTO.setNombre(tarea.getIdUsuariopk().getNombre());
		tareaDTO.setIdUsuario(empleadoDTO);
	}
}
