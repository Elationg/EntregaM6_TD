package com.bootcamp.springedumanager.repository;

import com.bootcamp.springedumanager.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para Curso - Leccion 3
 * Extiende JpaRepository para operaciones CRUD automaticas
 */
@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    List<Curso> findByCategoria(String categoria);

    List<Curso> findByNombreContainingIgnoreCase(String nombre);

    List<Curso> findByDocente(String docente);

    boolean existsByNombre(String nombre);

    @Query("SELECT c FROM Curso c WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :termino, '%')) " +
           "OR LOWER(c.docente) LIKE LOWER(CONCAT('%', :termino, '%')) " +
           "OR LOWER(c.categoria) LIKE LOWER(CONCAT('%', :termino, '%'))")
    List<Curso> buscarPorTermino(@Param("termino") String termino);

    @Query("SELECT DISTINCT c.categoria FROM Curso c ORDER BY c.categoria")
    List<String> findAllCategorias();
}
