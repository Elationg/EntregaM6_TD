package com.bootcamp.springedumanager.service.impl;

import com.bootcamp.springedumanager.model.Curso;
import com.bootcamp.springedumanager.repository.CursoRepository;
import com.bootcamp.springedumanager.service.CursoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementacion del servicio de Curso - Leccion 3
 * Inyeccion por constructor (buena practica recomendada por Spring)
 */
@Service
@Transactional
public class CursoServiceImpl implements CursoService {

    private final CursoRepository cursoRepository;

    public CursoServiceImpl(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Curso> findAll() {
        return cursoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> findById(Long id) {
        return cursoRepository.findById(id);
    }

    @Override
    public Curso save(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    public void deleteById(Long id) {
        cursoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Curso> buscarPorTermino(String termino) {
        if (termino == null || termino.isBlank()) {
            return cursoRepository.findAll();
        }
        return cursoRepository.buscarPorTermino(termino.trim());
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findAllCategorias() {
        return cursoRepository.findAllCategorias();
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return cursoRepository.count();
    }
}
