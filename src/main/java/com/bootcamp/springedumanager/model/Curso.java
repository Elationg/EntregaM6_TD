package com.bootcamp.springedumanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidad Curso - Leccion 2 (MVC) y Leccion 3 (JPA)
 */
@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del curso es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "La descripcion es obligatoria")
    @Size(max = 500, message = "La descripcion no puede superar los 500 caracteres")
    @Column(length = 500)
    private String descripcion;

    @NotNull(message = "La duracion en horas es obligatoria")
    @Min(value = 1, message = "La duracion minima es 1 hora")
    @Max(value = 500, message = "La duracion maxima es 500 horas")
    @Column(nullable = false)
    private Integer duracionHoras;

    @NotBlank(message = "El docente es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre del docente debe tener entre 3 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String docente;

    @NotBlank(message = "La categoria es obligatoria")
    @Column(nullable = false, length = 50)
    private String categoria;

    @ManyToMany(mappedBy = "cursos")
    private List<Estudiante> estudiantes = new ArrayList<>();

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evaluacion> evaluaciones = new ArrayList<>();

    public Curso() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Integer getDuracionHoras() { return duracionHoras; }
    public void setDuracionHoras(Integer duracionHoras) { this.duracionHoras = duracionHoras; }

    public String getDocente() { return docente; }
    public void setDocente(String docente) { this.docente = docente; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public List<Estudiante> getEstudiantes() { return estudiantes; }
    public void setEstudiantes(List<Estudiante> estudiantes) { this.estudiantes = estudiantes; }

    public List<Evaluacion> getEvaluaciones() { return evaluaciones; }
    public void setEvaluaciones(List<Evaluacion> evaluaciones) { this.evaluaciones = evaluaciones; }
}
