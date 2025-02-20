//package com.gsb.gsbecommercebackend.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class CorsConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")// Autoriser tous les endpoints
//                .allowedOrigins("http://localhost:5173", "http://localhost:5174") // adresses (url) autorisée à accéder à la ressource
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Méthodes HTTP autorisées
//                .allowedHeaders("Authorization", "Content-Type") /* Laisser à * si nécessaire */
//                .allowCredentials(true)
//                .exposedHeaders("Authorization"); // Autoriser les cookies
//
//    }
//}
