package com.bootcamp.springedumanager.controller;

import com.bootcamp.springedumanager.service.CursoService;
import com.bootcamp.springedumanager.service.EstudianteService;
import com.bootcamp.springedumanager.service.EvaluacionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador del Dashboard - Leccion 2 (Spring MVC)
 */
@Controller
public class HomeController {

    private final EstudianteService estudianteService;
    private final CursoService cursoService;
    private final EvaluacionService evaluacionService;

    public HomeController(EstudianteService estudianteService,
                          CursoService cursoService,
                          EvaluacionService evaluacionService) {
        this.estudianteService = estudianteService;
        this.cursoService = cursoService;
        this.evaluacionService = evaluacionService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("totalEstudiantes", estudianteService.count());
        model.addAttribute("totalCursos", cursoService.count());
        model.addAttribute("totalEvaluaciones", evaluacionService.count());
        model.addAttribute("ultimosCursos", cursoService.findAll().stream().limit(4).toList());
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
