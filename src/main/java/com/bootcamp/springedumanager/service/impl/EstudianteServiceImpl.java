package com.bootcamp.springedumanager.service.impl;

import com.bootcamp.springedumanager.model.Estudiante;
import com.bootcamp.springedumanager.repository.EstudianteRepository;
import com.bootcamp.springedumanager.service.EstudianteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementacion del servicio de Estudiante - Leccion 3
 * @Service registra el bean en el contexto de Spring
 * Inyeccion por constructor (buena practica recomendada por Spring)
 */
@Service
@Transactional
public class EstudianteServiceImpl implements EstudianteService {

    private final EstudianteRepository estudianteRepository;

    public EstudianteServiceImpl(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Estudiante> findAll() {
        return estudianteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Estudiante> findById(Long id) {
        return estudianteRepository.findById(id);
    }

    @Override
    public Estudiante save(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    @Override
    public void deleteById(Long id) {
        estudianteRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Estudiante> buscarPorTermino(String termino) {
        if (termino == null || termino.isBlank()) {
            return estudianteRepository.findAll();
        }
        return estudianteRepository.buscarPorTermino(termino.trim());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return estudianteRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByDni(String dni) {
        return estudianteRepository.existsByDni(dni);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return estudianteRepository.count();
    }
}
