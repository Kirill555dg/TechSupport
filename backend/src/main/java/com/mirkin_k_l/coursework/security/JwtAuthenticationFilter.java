package com.mirkin_k_l.coursework.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
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


                List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
                // Создание аутентификации
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(email, null, authorities);

                log.debug("Token: {}", token);
                log.debug("Extracted email: {}", email);
                log.debug("Extracted role: {}", role);
                log.debug("Authorities: {}", authorities);

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
