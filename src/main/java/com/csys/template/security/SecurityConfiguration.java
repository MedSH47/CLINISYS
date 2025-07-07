package com.csys.template.security;

import com.csys.template.config.JwtRequestFilter;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfiguration(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    /**
     * Configure le PasswordEncoder. Pour cet exemple, il ne chiffre pas le mot de
     * passe,
     * mais dans une application réelle, vous utiliseriez BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return (rawPassword == null) ? null : rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String storedPassword) {
                if (rawPassword == null || storedPassword == null) {
                    return false;
                }
                return rawPassword.toString().equals(storedPassword);
            }
        };
    }

    /**
     * Expose l'AuthenticationManager de Spring Security comme un bean.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * C'est ici que toute la chaîne de filtres de sécurité est configurée.
     * C'est la correction principale pour votre problème.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Active la configuration CORS (Cross-Origin Resource Sharing)
                .cors().and()
                // Désactive la protection CSRF (non nécessaire pour une API REST stateless)
                .csrf().disable()

                // Gère les exceptions d'authentification
                .exceptionHandling(exception -> exception.authenticationEntryPoint(new JwtAuthenticationEntryPoint()))

                // Configure la gestion de session pour qu'elle soit stateless, car on utilise
                // JWT
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Définit les autorisations pour les différentes requêtes HTTP
                .authorizeHttpRequests(auth -> auth
                        // 1. Endpoints publics qui ne nécessitent AUCUNE authentification
                        .antMatchers("/api/authenticate").permitAll()
                        .antMatchers("/api/forgot-password").permitAll()
                        .antMatchers("/api/verify-code").permitAll()
                        .antMatchers(HttpMethod.PUT, "**").permitAll()
                        .antMatchers(HttpMethod.GET, "**").permitAll()
                        .antMatchers("/api/geo/**").permitAll() // Pour les requêtes de géocodage
                        .antMatchers("/api/reset-password-jwt").permitAll()
                        .antMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // Pour la documentation Swagger
                        .antMatchers("/ws/**").permitAll() // Pour les WebSockets

                        // 2. Toutes les autres requêtes (/api/**) doivent être authentifiées
                        .anyRequest().authenticated())

                // Ajoute notre filtre personnalisé de validation JWT avant le filtre standard
                // de Spring
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)

                // Ajoute un filtre pour gérer les exceptions JWT très tôt dans la chaîne
                .addFilterBefore(new ExceptionHandlerFilter(), JwtRequestFilter.class);

        return http.build();
    }

    /**
     * Point d'entrée pour les erreurs d'authentification, renvoie une erreur 401
     * Unauthorized.
     */
    public static class JwtAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response,
                org.springframework.security.core.AuthenticationException authException) throws IOException {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Accès non autorisé. Un jeton d'authentification est requis.\"}");
        }

        @Override
        public void afterPropertiesSet() {
            setRealmName("JWT Realm");
            super.afterPropertiesSet();
        }
    }

    /**
     * Filtre pour attraper les exceptions liées à un JWT invalide avant qu'elles
     * n'atteignent le filtre principal.
     */
    public static class ExceptionHandlerFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                FilterChain filterChain) throws ServletException, IOException {
            try {
                filterChain.doFilter(request, response);
            } catch (io.jsonwebtoken.security.SignatureException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Signature du jeton JWT invalide\"}");
            } catch (io.jsonwebtoken.ExpiredJwtException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Jeton JWT expiré\"}");
            }
        }
    }
}