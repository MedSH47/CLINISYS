// Fichier : giminnn/CLINISYS/src/main/java/com/csys/template/security/WebConfig.java (Version Corrigée)
package com.csys.template.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        // Utiliser allowedOriginPatterns("*") est plus flexible et recommandé
                        // par rapport à allowedOrigins("*") avec allowCredentials(true).
                        .allowedOriginPatterns("*") 
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}