package com.bootcamp.springedumanager.service;

import com.bootcamp.springedumanager.model.Curso;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para Curso - Leccion 3
 */
public interface CursoService {

    List<Curso> findAll();

    Optional<Curso> findById(Long id);

    Curso save(Curso curso);

    void deleteById(Long id);

    List<Curso> buscarPorTermino(String termino);

    List<String> findAllCategorias();

    long count();
}
