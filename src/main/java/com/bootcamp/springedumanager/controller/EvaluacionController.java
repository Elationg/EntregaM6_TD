package com.bootcamp.springedumanager.controller;

import com.bootcamp.springedumanager.model.Evaluacion;
import com.bootcamp.springedumanager.service.CursoService;
import com.bootcamp.springedumanager.service.EstudianteService;
import com.bootcamp.springedumanager.service.EvaluacionService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador MVC para Evaluacion - Leccion 2, 3 y 4
 */
@Controller
@RequestMapping("/evaluaciones")
public class EvaluacionController {

    private static final String VISTA_FORMULARIO  = "evaluaciones/formulario";
    private static final String REDIRECT_LISTA    = "redirect:/evaluaciones";
    private static final String ATTR_ACCION       = "accion";
    private static final String ATTR_MENSAJE      = "mensaje";
    private static final String ATTR_TIPO         = "tipoMensaje";
    private static final String ATTR_ESTUDIANTES  = "estudiantes";
    private static final String ATTR_CURSOS       = "cursos";

    private final EvaluacionService evaluacionService;
    private final EstudianteService estudianteService;
    private final CursoService cursoService;

    public EvaluacionController(EvaluacionService evaluacionService,
                                EstudianteService estudianteService,
                                CursoService cursoService) {
        this.evaluacionService = evaluacionService;
        this.estudianteService = estudianteService;
        this.cursoService = cursoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("evaluaciones", evaluacionService.findAll());
        return "evaluaciones/lista";
    }

    @GetMapping("/nuevo")
    @PreAuthorize("hasRole('ADMIN')")
    public String nuevo(Model model) {
        model.addAttribute("evaluacion", new Evaluacion());
        model.addAttribute(ATTR_ESTUDIANTES, estudianteService.findAll());
        model.addAttribute(ATTR_CURSOS, cursoService.findAll());
        model.addAttribute(ATTR_ACCION, "Registrar");
        return VISTA_FORMULARIO;
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasRole('ADMIN')")
    @SuppressWarnings("java:S4684")
    public String guardar(@Valid @ModelAttribute Evaluacion evaluacion,
                          BindingResult result,
                          Model model,
                          RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute(ATTR_ESTUDIANTES, estudianteService.findAll());
            model.addAttribute(ATTR_CURSOS, cursoService.findAll());
            model.addAttribute(ATTR_ACCION, evaluacion.getId() == null ? "Registrar" : "Actualizar");
            return VISTA_FORMULARIO;
        }
        evaluacionService.save(evaluacion);
        flash.addFlashAttribute(ATTR_MENSAJE, "Evaluacion guardada correctamente");
        flash.addFlashAttribute(ATTR_TIPO, "success");
        return REDIRECT_LISTA;
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes flash) {
        return evaluacionService.findById(id)
                .map(ev -> {
                    model.addAttribute("evaluacion", ev);
                    model.addAttribute(ATTR_ESTUDIANTES, estudianteService.findAll());
                    model.addAttribute(ATTR_CURSOS, cursoService.findAll());
                    model.addAttribute(ATTR_ACCION, "Actualizar");
                    return VISTA_FORMULARIO;
                })
                .orElseGet(() -> {
                    flash.addFlashAttribute(ATTR_MENSAJE, "Evaluacion no encontrada");
                    flash.addFlashAttribute(ATTR_TIPO, "danger");
                    return REDIRECT_LISTA;
                });
    }

    @GetMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        evaluacionService.findById(id).ifPresentOrElse(
            ev -> {
                evaluacionService.deleteById(id);
                flash.addFlashAttribute(ATTR_MENSAJE, "Evaluacion eliminada correctamente");
                flash.addFlashAttribute(ATTR_TIPO, "success");
            },
            () -> {
                flash.addFlashAttribute(ATTR_MENSAJE, "Evaluacion no encontrada");
                flash.addFlashAttribute(ATTR_TIPO, "danger");
            }
        );
        return REDIRECT_LISTA;
    }
}
