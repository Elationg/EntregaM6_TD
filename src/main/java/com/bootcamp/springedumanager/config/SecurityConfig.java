package com.bootcamp.springedumanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuracion de Spring Security - Leccion 4
 *
 * Roles definidos:
 *  ADMIN - acceso total (crear/editar/eliminar cursos y evaluaciones)
 *  USER  - acceso de lectura (ver estudiantes, cursos y sus evaluaciones)
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    /**
     * Encoder de passwords con BCrypt (recomendado por Spring Security)
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Usuarios en memoria - Leccion 4
     * Para produccion: reemplazar con UserDetailsService que consulte la BD
     */
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails admin = User.builder()
                .username("admin")
                .password(encoder.encode("admin123"))
                .roles("ADMIN", "USER")
                .build();

        UserDetails user = User.builder()
                .username("user")
                .password(encoder.encode("user123"))
                .roles("USER")
                .build();

        UserDetails estudiante = User.builder()
                .username("estudiante")
                .password(encoder.encode("est123"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user, estudiante);
    }

    /**
     * Cadena de filtros de seguridad - Leccion 4
     * Define reglas de autorizacion por ruta y metodo HTTP
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Recursos publicos
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/login", "/logout").permitAll()

                // H2 Console (solo desarrollo)
                .requestMatchers("/h2-console/**").permitAll()

                // Rutas ADMIN: alta y eliminacion de cursos y evaluaciones
                .requestMatchers("/cursos/nuevo", "/cursos/guardar",
                                 "/cursos/editar/**", "/cursos/eliminar/**").hasRole("ADMIN")
                .requestMatchers("/evaluaciones/nuevo", "/evaluaciones/guardar",
                                 "/evaluaciones/editar/**", "/evaluaciones/eliminar/**").hasRole("ADMIN")
                .requestMatchers("/estudiantes/nuevo", "/estudiantes/guardar",
                                 "/estudiantes/editar/**", "/estudiantes/eliminar/**").hasRole("ADMIN")

                // API REST: requiere autenticacion (HTTP Basic para Postman)
                .requestMatchers("/api/**").authenticated()

                // Resto de rutas: usuario autenticado
                .anyRequest().authenticated()
            )

            // Formulario de login personalizado
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )

            // Logout
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )

            // HTTP Basic para consumo de REST con Postman
            .httpBasic(Customizer.withDefaults())

            // CSRF: deshabilitado para API REST y H2 Console
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/**", "/h2-console/**")
            )

            // Permitir frames para H2 Console
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())
            );

        return http.build();
    }
}
