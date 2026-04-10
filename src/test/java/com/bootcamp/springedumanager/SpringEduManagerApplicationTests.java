package com.bootcamp.springedumanager;

import com.bootcamp.springedumanager.model.Curso;
import com.bootcamp.springedumanager.model.Estudiante;
import com.bootcamp.springedumanager.service.CursoService;
import com.bootcamp.springedumanager.service.EstudianteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests de integracion - Leccion 1 (Maven lifecycle: test)
 * Valida que el contexto de Spring se levanta correctamente
 * y que los servicios principales funcionan.
 */
@SpringBootTest
class SpringEduManagerApplicationTests {

    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private CursoService cursoService;

    @Test
    void contextLoads() {
        // Verifica que el contexto de Spring Boot arranca sin errores
        assertThat(estudianteService).isNotNull();
        assertThat(cursoService).isNotNull();
    }

    @Test
    void estudiantesYCursosCargadosDesdeDataSql() {
        // Verifica que data.sql cargo los datos iniciales
        assertThat(estudianteService.count()).isGreaterThan(0);
        assertThat(cursoService.count()).isGreaterThan(0);
    }

    @Test
    void busquedaDeEstudianteFunciona() {
        var resultado = estudianteService.buscarPorTermino("Juan");
        assertThat(resultado).isNotEmpty();
    }

    @Test
    void busquedaDeCursoFunciona() {
        var resultado = cursoService.buscarPorTermino("Spring");
        assertThat(resultado).isNotEmpty();
    }
}
