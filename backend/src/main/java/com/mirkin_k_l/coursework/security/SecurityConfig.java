package com.mirkin_k_l.coursework.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/v1/auth/login", "/api/v1/auth/register", "/api/v1/application/create").permitAll()  // Разрешаем доступ к логину и регистрации
                        .requestMatchers("/api/v1/user/profile").authenticated()  // Доступ только для авторизованных пользователей
                        .requestMatchers("/api/v1/user/client", "/api/v1/application/client").hasRole("CLIENT")  // Доступ только для пользователей с ролью CLIENT
                        .requestMatchers("/api/v1/user/employee", "/api/v1/application").hasRole("EMPLOYEE")  // Доступ только для сотрудников
                        .requestMatchers("/api/v1/user/set-role").hasRole("ADMIN")
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Добавляем фильтр для проверки JWT
        return http.build(); // Возвращаем конфигурацию
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
