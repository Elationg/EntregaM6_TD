package com.bootcamp.springedumanager.controller.rest;

import com.bootcamp.springedumanager.model.Curso;
import com.bootcamp.springedumanager.service.CursoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controlador REST para Curso - Leccion 5
 *
 * Base URL: /api/cursos
 * Autenticacion: HTTP Basic (mismas credenciales que el login web)
 *
 * Ejemplos Postman:
 *   GET    http://localhost:8080/api/cursos              (user/user123)
 *   GET    http://localhost:8080/api/cursos/1            (user/user123)
 *   GET    http://localhost:8080/api/cursos/categorias   (user/user123)
 *   POST   http://localhost:8080/api/cursos              (admin/admin123)
 *   PUT    http://localhost:8080/api/cursos/1            (admin/admin123)
 *   DELETE http://localhost:8080/api/cursos/1            (admin/admin123)
 *
 * Nota: se usa la entidad directamente como DTO — aceptable en demos REST sin capa DTO
 */
@RestController
@RequestMapping("/api/cursos")
@SuppressWarnings("java:S4684")
public class CursoRestController {

    private static final String KEY_ERROR   = "error";
    private static final String KEY_MENSAJE = "mensaje";

    private final CursoService cursoService;

    public CursoRestController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public ResponseEntity<List<Curso>> listar(
            @RequestParam(required = false) String buscar) {
        List<Curso> lista = (buscar != null && !buscar.isBlank())
                ? cursoService.buscarPorTermino(buscar)
                : cursoService.findAll();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> obtenerPorId(@PathVariable Long id) {
        return cursoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/categorias")
    public ResponseEntity<List<String>> categorias() {
        return ResponseEntity.ok(cursoService.findAllCategorias());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> crear(@Valid @RequestBody Curso curso) {
        cursoService.save(curso);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(KEY_MENSAJE, "Curso creado correctamente"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> actualizar(@PathVariable Long id,
                                                          @Valid @RequestBody Curso curso) {
        if (cursoService.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(KEY_ERROR, "Curso no encontrado"));
        }
        curso.setId(id);
        cursoService.save(curso);
        return ResponseEntity.ok(Map.of(KEY_MENSAJE, "Curso actualizado correctamente"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id) {
        if (cursoService.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(KEY_ERROR, "Curso no encontrado"));
        }
        cursoService.deleteById(id);
        return ResponseEntity.ok(Map.of(KEY_MENSAJE, "Curso eliminado correctamente"));
    }
}
