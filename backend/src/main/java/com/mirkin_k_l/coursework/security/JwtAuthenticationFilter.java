package com.mirkin_k_l.coursework.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Извлечение JWT токена из заголовка Authorization
        String token = extractJwtFromRequest(request);

        // Если токен существует и валиден, аутентифицируем пользователя
        if (token != null && jwtUtil.isTokenValid(token, jwtUtil.extractEmail(token))) {
            try {
                // Извлечение информации о пользователе из токена
                String email = jwtUtil.extractEmail(token);
                String role = jwtUtil.extractRole(token);

                // Создание аутентификации
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(email, null, null);

                // Устанавливаем роль пользователя в контексте безопасности
                authentication.setDetails(role);

                // Сохраняем аутентификацию в SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JwtException e) {
                // Если токен недействителен или истек, возвращаем ошибку
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired JWT token.");
                return;
            }
        }

        // Продолжаем выполнение фильтра
        filterChain.doFilter(request, response);
    }

    // Извлечение токена из заголовка Authorization
    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Возвращаем сам токен без "Bearer "
        }
        return null; // Если токен не найден, возвращаем null
    }
}
