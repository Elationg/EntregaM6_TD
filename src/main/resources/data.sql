-- ============================================
-- SpringEduManager - Datos iniciales (data.sql)
-- Leccion 3: Persistencia con JPA + H2
-- Se ejecuta automaticamente al iniciar la app
-- ============================================

-- Cursos de ejemplo
INSERT INTO cursos (nombre, descripcion, duracion_horas, docente, categoria)
VALUES ('Spring Boot Avanzado',
        'Desarrollo de aplicaciones JEE con Spring Boot, MVC, JPA y Security.',
        80, 'Prof. Carlos Mendez', 'Backend');

INSERT INTO cursos (nombre, descripcion, duracion_horas, docente, categoria)
VALUES ('Desarrollo Web con React',
        'Construccion de interfaces modernas con React, Hooks y Context API.',
        60, 'Prof. Ana Torres', 'Frontend');

INSERT INTO cursos (nombre, descripcion, duracion_horas, docente, categoria)
VALUES ('Bases de Datos Relacionales',
        'Diseno y administracion de bases de datos con SQL, MySQL y PostgreSQL.',
        40, 'Prof. Luis Gomez', 'Datos');

INSERT INTO cursos (nombre, descripcion, duracion_horas, docente, categoria)
VALUES ('DevOps y CI/CD',
        'Automatizacion con Docker, Jenkins y pipelines de integracion continua.',
        50, 'Prof. Maria Lopez', 'DevOps');

INSERT INTO cursos (nombre, descripcion, duracion_horas, docente, categoria)
VALUES ('Python para Data Science',
        'Analisis de datos con Python, Pandas, NumPy y visualizacion.',
        70, 'Prof. Roberto Silva', 'Datos');

-- Estudiantes de ejemplo
INSERT INTO estudiantes (nombre, apellido, email, dni)
VALUES ('Juan', 'Perez', 'juan.perez@email.com', '12345678');

INSERT INTO estudiantes (nombre, apellido, email, dni)
VALUES ('Maria', 'Garcia', 'maria.garcia@email.com', '23456789');

INSERT INTO estudiantes (nombre, apellido, email, dni)
VALUES ('Carlos', 'Lopez', 'carlos.lopez@email.com', '34567890');

INSERT INTO estudiantes (nombre, apellido, email, dni)
VALUES ('Ana', 'Martinez', 'ana.martinez@email.com', '45678901');

INSERT INTO estudiantes (nombre, apellido, email, dni)
VALUES ('Luis', 'Rodriguez', 'luis.rodriguez@email.com', '56789012');

-- Evaluaciones de ejemplo
INSERT INTO evaluaciones (titulo, nota, fecha, observaciones, estudiante_id, curso_id)
VALUES ('Parcial 1 - Spring MVC', 8.5, '2026-03-15', 'Buen manejo de controladores', 1, 1);

INSERT INTO evaluaciones (titulo, nota, fecha, observaciones, estudiante_id, curso_id)
VALUES ('Trabajo Practico - React Hooks', 9.0, '2026-03-20', 'Excelente implementacion', 2, 2);

INSERT INTO evaluaciones (titulo, nota, fecha, observaciones, estudiante_id, curso_id)
VALUES ('Examen Final - SQL', 7.5, '2026-03-25', 'Aprobado con buena nota', 3, 3);

INSERT INTO evaluaciones (titulo, nota, fecha, observaciones, estudiante_id, curso_id)
VALUES ('Parcial 1 - Spring MVC', 6.0, '2026-03-15', 'Debe reforzar seguridad', 4, 1);

INSERT INTO evaluaciones (titulo, nota, fecha, observaciones, estudiante_id, curso_id)
VALUES ('Proyecto Final - Data Science', 9.5, '2026-03-28', 'Excelente proyecto', 5, 5);
