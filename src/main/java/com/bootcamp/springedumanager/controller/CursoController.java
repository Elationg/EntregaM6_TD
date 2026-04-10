package com.bootcamp.springedumanager.controller;

import com.bootcamp.springedumanager.model.Curso;
import com.bootcamp.springedumanager.service.CursoService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador MVC para Curso - Leccion 2 y 4
 * La creacion de cursos esta restringida al rol ADMIN (Leccion 4)
 */
@Controller
@RequestMapping("/cursos")
public class CursoController {

    private static final String VISTA_FORMULARIO = "cursos/formulario";
    private static final String REDIRECT_LISTA   = "redirect:/cursos";
    private static final String ATTR_ACCION      = "accion";
    private static final String ATTR_MENSAJE     = "mensaje";
    private static final String ATTR_TIPO        = "tipoMensaje";

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) String buscar, Model model) {
        var lista = (buscar != null && !buscar.isBlank())
                ? cursoService.buscarPorTermino(buscar)
                : cursoService.findAll();
        model.addAttribute("cursos", lista);
        model.addAttribute("buscar", buscar);
        model.addAttribute("categorias", cursoService.findAllCategorias());
        return "cursos/lista";
    }

    @GetMapping("/nuevo")
    @PreAuthorize("hasRole('ADMIN')")
    public String nuevo(Model model) {
        model.addAttribute("curso", new Curso());
        model.addAttribute(ATTR_ACCION, "Crear");
        return VISTA_FORMULARIO;
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasRole('ADMIN')")
    @SuppressWarnings("java:S4684")
    public String guardar(@Valid @ModelAttribute Curso curso,
                          BindingResult result,
                          Model model,
                          RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute(ATTR_ACCION, curso.getId() == null ? "Crear" : "Actualizar");
            return VISTA_FORMULARIO;
        }
        cursoService.save(curso);
        flash.addFlashAttribute(ATTR_MENSAJE, "Curso guardado correctamente");
        flash.addFlashAttribute(ATTR_TIPO, "success");
        return REDIRECT_LISTA;
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes flash) {
        return cursoService.findById(id)
                .map(c -> {
                    model.addAttribute("curso", c);
                    model.addAttribute(ATTR_ACCION, "Actualizar");
                    return VISTA_FORMULARIO;
                })
                .orElseGet(() -> {
                    flash.addFlashAttribute(ATTR_MENSAJE, "Curso no encontrado");
                    flash.addFlashAttribute(ATTR_TIPO, "danger");
                    return REDIRECT_LISTA;
                });
    }

    @GetMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        cursoService.findById(id).ifPresentOrElse(
            c -> {
                cursoService.deleteById(id);
                flash.addFlashAttribute(ATTR_MENSAJE, "Curso eliminado correctamente");
                flash.addFlashAttribute(ATTR_TIPO, "success");
            },
            () -> {
                flash.addFlashAttribute(ATTR_MENSAJE, "Curso no encontrado");
                flash.addFlashAttribute(ATTR_TIPO, "danger");
            }
        );
        return REDIRECT_LISTA;
    }
}
