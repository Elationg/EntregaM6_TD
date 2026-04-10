package com.bootcamp.springedumanager.controller.rest;

import com.bootcamp.springedumanager.model.Estudiante;
import com.bootcamp.springedumanager.service.EstudianteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controlador REST para Estudiante - Leccion 5
 *
 * Base URL: /api/estudiantes
 * Autenticacion: HTTP Basic (mismas credenciales que el login web)
 *
 * Ejemplos Postman:
 *   GET    http://localhost:8080/api/estudiantes         (user/user123)
 *   GET    http://localhost:8080/api/estudiantes/1       (user/user123)
 *   POST   http://localhost:8080/api/estudiantes         (admin/admin123)
 *   PUT    http://localhost:8080/api/estudiantes/1       (admin/admin123)
 *   DELETE http://localhost:8080/api/estudiantes/1       (admin/admin123)
 *
 * Nota: se usa la entidad directamente como DTO — aceptable en demos REST sin capa DTO
 */
@RestController
@RequestMapping("/api/estudiantes")
@SuppressWarnings("java:S4684")
public class EstudianteRestController {

    private static final String KEY_ERROR   = "error";
    private static final String KEY_MENSAJE = "mensaje";

    private final EstudianteService estudianteService;

    public EstudianteRestController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @GetMapping
    public ResponseEntity<List<Estudiante>> listar(
            @RequestParam(required = false) String buscar) {
        List<Estudiante> lista = (buscar != null && !buscar.isBlank())
                ? estudianteService.buscarPorTermino(buscar)
                : estudianteService.findAll();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> obtenerPorId(@PathVariable Long id) {
        return estudianteService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> crear(@Valid @RequestBody Estudiante estudiante) {
        if (estudianteService.existsByEmail(estudiante.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(Map.of(KEY_ERROR, "Ya existe un estudiante con ese email"));
        }
        if (estudianteService.existsByDni(estudiante.getDni())) {
            return ResponseEntity.badRequest()
                    .body(Map.of(KEY_ERROR, "Ya existe un estudiante con ese DNI"));
        }
        estudianteService.save(estudiante);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(KEY_MENSAJE, "Estudiante creado correctamente"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> actualizar(@PathVariable Long id,
                                                          @Valid @RequestBody Estudiante estudiante) {
        if (estudianteService.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(KEY_ERROR, "Estudiante no encontrado"));
        }
        estudiante.setId(id);
        estudianteService.save(estudiante);
        return ResponseEntity.ok(Map.of(KEY_MENSAJE, "Estudiante actualizado correctamente"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id) {
        if (estudianteService.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(KEY_ERROR, "Estudiante no encontrado"));
        }
        estudianteService.deleteById(id);
        return ResponseEntity.ok(Map.of(KEY_MENSAJE, "Estudiante eliminado correctamente"));
    }
}
