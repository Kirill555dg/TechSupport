package com.mirkin_k_l.coursework;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Разрешить запросы ко всем эндпоинтам
                .allowedOrigins("http://localhost:8008") // Разрешить только с указанного источника
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Разрешить методы
                .allowedHeaders("*") // Разрешить любые заголовки
                .allowCredentials(true); // Разрешить отправку куки, если нужно
    }
}