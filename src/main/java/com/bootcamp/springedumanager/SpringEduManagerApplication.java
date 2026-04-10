package com.bootcamp.springedumanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de arranque - SpringEduManager
 * M6: Desarrollo de aplicaciones JEE con Spring Framework
 *
 * Lecciones cubiertas:
 *  1 - Maven como gestor de ciclo de vida
 *  2 - Spring MVC (controladores, vistas Thymeleaf)
 *  3 - Acceso a datos con Spring Data JPA + H2
 *  4 - Seguridad con Spring Security (roles ADMIN/USER)
 *  5 - APIs RESTful con @RestController
 */
@SpringBootApplication
public class SpringEduManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringEduManagerApplication.class, args);
    }
}
