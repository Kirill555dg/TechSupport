package com.mirkin_k_l.coursework.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {


    private final String SECRET_KEY;
    private final Long EXPIRATION_TIME;

    public JwtUtil(@Value("${test.security.secret}") String secretKey,
                   @Value("${test.security.expiration-time:3600000}") Long expirationTime) {
        SECRET_KEY = secretKey;
        EXPIRATION_TIME = expirationTime;
    }


    private Key getSigningKey() {
        // Декодируем Base64 строку в байты и генерируем ключ
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String email, String role) {
        return Jwts.builder()
                .subject(email) // Email как основной claim
                .claim("role", role) // Добавляем роль как кастомный claim
                .issuedAt(new Date()) // Устанавливаем дату выпуска
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Устанавливаем срок действия
                .signWith(getSigningKey()) // Используем ключ для подписи
                .compact();
    }

    /**
     * Извлечение Claims из токена
     */
    public Claims extractClaims(String token) {
        JwtParser parser = Jwts.parser()
                .setSigningKey(getSigningKey()) // Установка ключа для проверки подписи
                .build();

        return parser.parseClaimsJws(token).getBody();
    }

    /**
     * Проверка валидности токена
     */
    public boolean isTokenValid(String token, String email) {
        Claims claims = extractClaims(token);
        return claims.getSubject().equals(email) && !isTokenExpired(token);
    }

    /**
     * Проверка истечения срока действия токена
     */
    private boolean isTokenExpired(String token) {
        Claims claims = extractClaims(token);
        return claims.getExpiration().before(new Date());
    }

    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return extractClaims(token).get("role", String.class);
    }

}
