package com.bootcamp.springedumanager.repository;

import com.bootcamp.springedumanager.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para Estudiante - Leccion 3
 * Extiende JpaRepository para operaciones CRUD automaticas
 */
@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    Optional<Estudiante> findByEmail(String email);

    Optional<Estudiante> findByDni(String dni);

    List<Estudiante> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(
            String nombre, String apellido);

    boolean existsByEmail(String email);

    boolean existsByDni(String dni);

    @Query("SELECT e FROM Estudiante e WHERE LOWER(e.nombre) LIKE LOWER(CONCAT('%', :termino, '%')) " +
           "OR LOWER(e.apellido) LIKE LOWER(CONCAT('%', :termino, '%')) " +
           "OR LOWER(e.email) LIKE LOWER(CONCAT('%', :termino, '%'))")
    List<Estudiante> buscarPorTermino(@Param("termino") String termino);
}
