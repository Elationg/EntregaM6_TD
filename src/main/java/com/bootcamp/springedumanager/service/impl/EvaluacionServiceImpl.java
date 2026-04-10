package com.bootcamp.springedumanager.service.impl;

import com.bootcamp.springedumanager.model.Evaluacion;
import com.bootcamp.springedumanager.repository.EvaluacionRepository;
import com.bootcamp.springedumanager.service.EvaluacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementacion del servicio de Evaluacion - Leccion 3
 * Inyeccion por constructor (buena practica recomendada por Spring)
 */
@Service
@Transactional
public class EvaluacionServiceImpl implements EvaluacionService {

    private final EvaluacionRepository evaluacionRepository;

    public EvaluacionServiceImpl(EvaluacionRepository evaluacionRepository) {
        this.evaluacionRepository = evaluacionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Evaluacion> findAll() {
        return evaluacionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Evaluacion> findById(Long id) {
        return evaluacionRepository.findById(id);
    }

    @Override
    public Evaluacion save(Evaluacion evaluacion) {
        return evaluacionRepository.save(evaluacion);
    }

    @Override
    public void deleteById(Long id) {
        evaluacionRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Evaluacion> findByEstudianteId(Long estudianteId) {
        return evaluacionRepository.findByEstudianteId(estudianteId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Evaluacion> findByCursoId(Long cursoId) {
        return evaluacionRepository.findByCursoId(cursoId);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return evaluacionRepository.count();
    }
}
