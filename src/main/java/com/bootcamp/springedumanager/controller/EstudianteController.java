package com.bootcamp.springedumanager.controller;

import com.bootcamp.springedumanager.model.Estudiante;
import com.bootcamp.springedumanager.service.EstudianteService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador MVC para Estudiante - Leccion 2 y 4
 */
@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {

    private static final String VISTA_FORMULARIO = "estudiantes/formulario";
    private static final String REDIRECT_LISTA   = "redirect:/estudiantes";
    private static final String ATTR_ACCION      = "accion";
    private static final String ATTR_MENSAJE     = "mensaje";
    private static final String ATTR_TIPO        = "tipoMensaje";

    private final EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) String buscar, Model model) {
        var lista = (buscar != null && !buscar.isBlank())
                ? estudianteService.buscarPorTermino(buscar)
                : estudianteService.findAll();
        model.addAttribute("estudiantes", lista);
        model.addAttribute("buscar", buscar);
        return "estudiantes/lista";
    }

    @GetMapping("/nuevo")
    @PreAuthorize("hasRole('ADMIN')")
    public String nuevo(Model model) {
        model.addAttribute("estudiante", new Estudiante());
        model.addAttribute(ATTR_ACCION, "Registrar");
        return VISTA_FORMULARIO;
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasRole('ADMIN')")
    // Entidad usada directamente como model object — aceptable en demos MVC sin capa DTO
    @SuppressWarnings("java:S4684")
    public String guardar(@Valid @ModelAttribute Estudiante estudiante,
                          BindingResult result,
                          Model model,
                          RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute(ATTR_ACCION, estudiante.getId() == null ? "Registrar" : "Actualizar");
            return VISTA_FORMULARIO;
        }
        estudianteService.save(estudiante);
        flash.addFlashAttribute(ATTR_MENSAJE, "Estudiante guardado correctamente");
        flash.addFlashAttribute(ATTR_TIPO, "success");
        return REDIRECT_LISTA;
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes flash) {
        return estudianteService.findById(id)
                .map(e -> {
                    model.addAttribute("estudiante", e);
                    model.addAttribute(ATTR_ACCION, "Actualizar");
                    return VISTA_FORMULARIO;
                })
                .orElseGet(() -> {
                    flash.addFlashAttribute(ATTR_MENSAJE, "Estudiante no encontrado");
                    flash.addFlashAttribute(ATTR_TIPO, "danger");
                    return REDIRECT_LISTA;
                });
    }

    @GetMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        estudianteService.findById(id).ifPresentOrElse(
            e -> {
                estudianteService.deleteById(id);
                flash.addFlashAttribute(ATTR_MENSAJE, "Estudiante eliminado correctamente");
                flash.addFlashAttribute(ATTR_TIPO, "success");
            },
            () -> {
                flash.addFlashAttribute(ATTR_MENSAJE, "Estudiante no encontrado");
                flash.addFlashAttribute(ATTR_TIPO, "danger");
            }
        );
        return REDIRECT_LISTA;
    }
}
