package com.bootcamp.springedumanager.service;

import com.bootcamp.springedumanager.model.Estudiante;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para Estudiante - Leccion 3
 */
public interface EstudianteService {

    List<Estudiante> findAll();

    Optional<Estudiante> findById(Long id);

    Estudiante save(Estudiante estudiante);

    void deleteById(Long id);

    List<Estudiante> buscarPorTermino(String termino);

    boolean existsByEmail(String email);

    boolean existsByDni(String dni);

    long count();
}
