# SpringEduManager

> **M6 - Desarrollo de aplicaciones JEE con Spring Framework**
> Evaluación de módulo — Bootcamp de Programación

Plataforma web educativa para gestión de estudiantes, cursos y evaluaciones, desarrollada con el ecosistema de Spring.

---

## Tecnologías utilizadas

| Capa | Tecnología |
|------|-----------|
| Lenguaje | Java 17 |
| Framework | Spring Boot 3.2.4 |
| Web | Spring MVC + Thymeleaf |
| Persistencia | Spring Data JPA + H2 |
| Seguridad | Spring Security 6 |
| REST | Spring Web (@RestController) |
| Build | Maven |
| UI | Bootstrap 5 + Bootstrap Icons |

---

## Estructura del proyecto

```
SpringEduManager/
├── pom.xml                                         # Lección 1 - Gestor de proyectos
└── src/
    ├── main/
    │   ├── java/com/bootcamp/springedumanager/
    │   │   ├── SpringEduManagerApplication.java    # Punto de entrada
    │   │   ├── config/
    │   │   │   └── SecurityConfig.java             # Lección 4 - Spring Security
    │   │   ├── model/
    │   │   │   ├── Estudiante.java                 # Lección 2 - Entidad JPA
    │   │   │   ├── Curso.java                      # Lección 2 - Entidad JPA
    │   │   │   └── Evaluacion.java                 # Entidad JPA
    │   │   ├── repository/
    │   │   │   ├── EstudianteRepository.java       # Lección 3 - JpaRepository
    │   │   │   ├── CursoRepository.java            # Lección 3 - JpaRepository
    │   │   │   └── EvaluacionRepository.java       # Lección 3 - JpaRepository
    │   │   ├── service/
    │   │   │   ├── EstudianteService.java          # Lección 3 - Interfaz
    │   │   │   ├── CursoService.java               # Lección 3 - Interfaz
    │   │   │   ├── EvaluacionService.java          # Lección 3 - Interfaz
    │   │   │   └── impl/
    │   │   │       ├── EstudianteServiceImpl.java  # Lección 3 - @Service
    │   │   │       ├── CursoServiceImpl.java       # Lección 3 - @Service
    │   │   │       └── EvaluacionServiceImpl.java  # Lección 3 - @Service
    │   │   └── controller/
    │   │       ├── HomeController.java             # Lección 2 - Dashboard
    │   │       ├── EstudianteController.java       # Lección 2 - MVC
    │   │       ├── CursoController.java            # Lección 2 - MVC
    │   │       ├── EvaluacionController.java       # Lección 2 - MVC
    │   │       └── rest/
    │   │           ├── EstudianteRestController.java  # Lección 5 - REST
    │   │           └── CursoRestController.java       # Lección 5 - REST
    │   └── resources/
    │       ├── application.properties              # Configuración JPA, H2, Thymeleaf
    │       ├── data.sql                            # Datos iniciales
    │       └── templates/
    │           ├── fragments/navbar.html           # Fragmento reutilizable
    │           ├── index.html                      # Dashboard
    │           ├── login.html                      # Lección 4 - Formulario de login
    │           ├── estudiantes/
    │           │   ├── lista.html
    │           │   └── formulario.html
    │           ├── cursos/
    │           │   ├── lista.html
    │           │   └── formulario.html
    │           └── evaluaciones/
    │               ├── lista.html
    │               └── formulario.html
    └── test/
        └── java/com/bootcamp/springedumanager/
            └── SpringEduManagerApplicationTests.java
```

---

## Lecciones del módulo

### Lección 1 — El gestor de proyectos (Maven)

El proyecto usa **Maven** como gestor del ciclo de vida. Comandos principales:

```bash
# Limpiar artefactos previos
mvn clean

# Compilar, testear e instalar en repositorio local
mvn install

# Generar el JAR ejecutable en /target
mvn package

# Ejecutar la aplicación
mvn spring-boot:run
```

Las dependencias están definidas en `pom.xml`:
- `spring-boot-starter-web`
- `spring-boot-starter-data-jpa`
- `spring-boot-starter-security`
- `spring-boot-starter-thymeleaf`
- `h2` (base de datos embebida)

---

### Lección 2 — Spring MVC

Implementación del patrón **Model-View-Controller**:

- **Modelos**: `Estudiante`, `Curso`, `Evaluacion` (anotadas con `@Entity`)
- **Controladores**: `@Controller` con rutas `@GetMapping` / `@PostMapping`
- **Vistas**: Thymeleaf (`templates/`) con formularios y listados

Rutas web principales:

| Método | URL | Descripción |
|--------|-----|-------------|
| GET | `/` | Dashboard |
| GET | `/estudiantes` | Listar estudiantes |
| GET | `/estudiantes/nuevo` | Formulario nuevo estudiante |
| POST | `/estudiantes/guardar` | Guardar estudiante |
| GET | `/cursos` | Listar cursos |
| GET | `/cursos/nuevo` | Formulario nuevo curso |
| POST | `/cursos/guardar` | Guardar curso |
| GET | `/evaluaciones` | Listar evaluaciones |

---

### Lección 3 — Acceso a datos con Spring Data JPA

- **Base de datos**: H2 embebida en memoria
- **Repositorios**: `EstudianteRepository`, `CursoRepository`, `EvaluacionRepository` extienden `JpaRepository`
- **Servicios**: `@Service` con lógica de negocio e inyección de dependencias
- **Consola H2**: disponible en `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:springedumanager`
  - Usuario: `sa` | Contraseña: *(vacía)*

---

### Lección 4 — Spring Security

Configuración en `SecurityConfig.java`:

| Usuario | Contraseña | Rol |
|---------|-----------|-----|
| `admin` | `admin123` | ADMIN + USER |
| `user` | `user123` | USER |
| `estudiante` | `est123` | USER |

**Reglas de acceso:**
- `ADMIN`: puede crear, editar y eliminar cursos, estudiantes y evaluaciones
- `USER`: puede ver todos los listados pero no modificar datos
- Rutas públicas: `/login`, `/logout`

---

### Lección 5 — APIs RESTful

**Base URL**: `http://localhost:8080/api`

Autenticación: **HTTP Basic** (usar las mismas credenciales de arriba en Postman)

#### Estudiantes

```
GET    /api/estudiantes           → Lista todos
GET    /api/estudiantes/{id}      → Obtiene uno por ID
POST   /api/estudiantes           → Crea (ADMIN)
PUT    /api/estudiantes/{id}      → Actualiza (ADMIN)
DELETE /api/estudiantes/{id}      → Elimina (ADMIN)
```

#### Cursos

```
GET    /api/cursos                → Lista todos
GET    /api/cursos/{id}           → Obtiene uno por ID
GET    /api/cursos/categorias     → Lista categorías
POST   /api/cursos                → Crea (ADMIN)
PUT    /api/cursos/{id}           → Actualiza (ADMIN)
DELETE /api/cursos/{id}           → Elimina (ADMIN)
```

**Ejemplo de body JSON para POST /api/cursos:**
```json
{
  "nombre": "Microservicios con Spring Cloud",
  "descripcion": "Arquitectura de microservicios con Spring Cloud y Kubernetes",
  "duracionHoras": 60,
  "docente": "Prof. Sandra Diaz",
  "categoria": "Backend"
}
```

**Ejemplo de body JSON para POST /api/estudiantes:**
```json
{
  "nombre": "Sofia",
  "apellido": "Fernandez",
  "email": "sofia.fernandez@email.com",
  "dni": "78901234"
}
```

---

## Cómo ejecutar

### Requisitos
- Java 17+
- Maven 3.6+

### Pasos

```bash
# 1. Clonar o descomprimir el proyecto
cd SpringEduManager

# 2. Compilar y ejecutar (Lección 1 - ciclo de vida Maven)
mvn clean install
mvn spring-boot:run

# 3. Acceder a la aplicación
# Web:         http://localhost:8080
# Login:       http://localhost:8080/login
# H2 Console:  http://localhost:8080/h2-console
# API REST:    http://localhost:8080/api/estudiantes
```

### Usar MySQL en lugar de H2

1. Descomentar el conector MySQL en `pom.xml`
2. En `application.properties`, comentar la sección H2 y descomentar la sección MySQL
3. Crear la base de datos: `CREATE DATABASE springedumanager;`
4. Actualizar usuario y contraseña en `application.properties`

---

## Validaciones implementadas

- Formularios con Bean Validation (`@NotBlank`, `@Email`, `@Pattern`, `@Min`, `@Max`)
- Mensajes de error en español mostrados en los formularios
- Validación del lado servidor en controladores con `BindingResult`
- Control de duplicados (email y DNI únicos)

---

## Referencias

- [Spring Boot Docs](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/)
- [Thymeleaf Documentation](https://www.thymeleaf.org/documentation.html)
- [Baeldung Spring Tutorials](https://www.baeldung.com/spring-tutorial)
