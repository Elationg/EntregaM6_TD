package com.bootcamp.springedumanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidad Estudiante - Leccion 2 (MVC) y Leccion 3 (JPA)
 */
@Entity
@Table(name = "estudiantes")
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    @Column(nullable = false, length = 50)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    @Column(nullable = false, length = 50)
    private String apellido;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ingresar un email valido")
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "\\d{7,10}", message = "El DNI debe tener entre 7 y 10 digitos numericos")
    @Column(nullable = false, unique = true, length = 15)
    private String dni;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "estudiante_curso",
        joinColumns = @JoinColumn(name = "estudiante_id"),
        inverseJoinColumns = @JoinColumn(name = "curso_id")
    )
    private List<Curso> cursos = new ArrayList<>();

    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evaluacion> evaluaciones = new ArrayList<>();

    public Estudiante() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public List<Curso> getCursos() { return cursos; }
    public void setCursos(List<Curso> cursos) { this.cursos = cursos; }

    public List<Evaluacion> getEvaluaciones() { return evaluaciones; }
    public void setEvaluaciones(List<Evaluacion> evaluaciones) { this.evaluaciones = evaluaciones; }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
}
