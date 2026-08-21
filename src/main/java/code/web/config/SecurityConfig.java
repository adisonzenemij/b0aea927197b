package code.web.config;

import code.utility.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/** Configura JWT solo para los recursos administrativos indicados. */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    /** Crea el codificador BCrypt usado para almacenar y validar contraseñas. */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    /**
     * Exige JWT para todo roles y usuarios. En los demás controladores, solo las
     * operaciones de escritura requieren JWT; sus consultas permanecen públicas.
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers("/api/auth/**")
                                .permitAll()
                                .requestMatchers("/api/role-data/**", "/api/user-data/**")
                                .authenticated()
                                .requestMatchers(HttpMethod.POST, "/api/**")
                                .authenticated()
                                .requestMatchers(HttpMethod.PUT, "/api/**")
                                .authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/api/**")
                                .authenticated()
                                .requestMatchers(
                                        "/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html",
                                        "/scalar/**",
                                        "/actuator/**")
                                .permitAll()
                                .anyRequest()
                                .permitAll())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(
                        exception -> exception
                                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .build();
    }
}
