package com.bootcamp.springedumanager.repository;

import com.bootcamp.springedumanager.model.Evaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para Evaluacion - Leccion 3
 */
@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {

    List<Evaluacion> findByEstudianteId(Long estudianteId);

    List<Evaluacion> findByCursoId(Long cursoId);

    @Query("SELECT AVG(e.nota) FROM Evaluacion e WHERE e.estudiante.id = :estudianteId")
    Double promedioNotasPorEstudiante(@Param("estudianteId") Long estudianteId);

    @Query("SELECT AVG(e.nota) FROM Evaluacion e WHERE e.curso.id = :cursoId")
    Double promedioNotasPorCurso(@Param("cursoId") Long cursoId);
}
