package com.co.prueba.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import com.co.prueba.domain.Tarea;

@Component
public interface TareaRepository extends CrudRepository<Tarea, Long> {

	@Query("SELECT t FROM Tarea t WHERE t.fechaEjecucion <= :fecha AND (t.fechaCierre = NULL OR t.fechaCierre >= :fecha)")
	List<Tarea> findAllTareasFecha(@Param("fecha") Date fecha);

}
