package com.bootcamp.springedumanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

/**
 * Entidad Evaluacion - Leccion 3 (JPA)
 * Vincula un Estudiante con un Curso y registra su calificacion
 */
@Entity
@Table(name = "evaluaciones")
public class Evaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El titulo de la evaluacion es obligatorio")
    @Size(min = 3, max = 150, message = "El titulo debe tener entre 3 y 150 caracteres")
    @Column(nullable = false, length = 150)
    private String titulo;

    @NotNull(message = "La nota es obligatoria")
    @DecimalMin(value = "0.0", message = "La nota minima es 0")
    @DecimalMax(value = "10.0", message = "La nota maxima es 10")
    @Column(nullable = false)
    private Double nota;

    @NotNull(message = "La fecha es obligatoria")
    @Column(nullable = false)
    private LocalDate fecha;

    @Column(length = 300)
    private String observaciones;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_id", nullable = false)
    @NotNull(message = "Debe seleccionar un estudiante")
    private Estudiante estudiante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    @NotNull(message = "Debe seleccionar un curso")
    private Curso curso;

    // Constructor sin argumentos requerido por JPA
    public Evaluacion() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public Double getNota() { return nota; }
    public void setNota(Double nota) { this.nota = nota; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public Estudiante getEstudiante() { return estudiante; }
    public void setEstudiante(Estudiante estudiante) { this.estudiante = estudiante; }

    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }

    public String getEstadoNota() {
        if (nota >= 7.0) return "Aprobado";
        if (nota >= 4.0) return "Regular";
        return "Desaprobado";
    }
}
