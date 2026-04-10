package com.bootcamp.springedumanager.service;

import com.bootcamp.springedumanager.model.Evaluacion;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para Evaluacion - Leccion 3
 */
public interface EvaluacionService {

    List<Evaluacion> findAll();

    Optional<Evaluacion> findById(Long id);

    Evaluacion save(Evaluacion evaluacion);

    void deleteById(Long id);

    List<Evaluacion> findByEstudianteId(Long estudianteId);

    List<Evaluacion> findByCursoId(Long cursoId);

    long count();
}
