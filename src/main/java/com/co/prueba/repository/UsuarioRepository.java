package com.co.prueba.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.co.prueba.domain.Usuario;

@Component
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

}
