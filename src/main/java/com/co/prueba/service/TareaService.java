package com.co.prueba.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.co.prueba.datatransfer.Respuesta;
import com.co.prueba.datatransfer.TareaDTO;

import fj.data.Either;

@Service
public interface TareaService {

	public Either<Exception, Respuesta> crearTarea(TareaDTO tareaDTO);

	public Either<Exception, Respuesta> eliminarTarea(Long tareaId);

	public Either<Exception, List<TareaDTO>> listarTodasTareas();

	public Either<Exception, List<TareaDTO>> listarTareasUsuarioFecha(Date fecha);

}
