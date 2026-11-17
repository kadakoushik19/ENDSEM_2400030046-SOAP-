package com.example.jwtvalidation.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final SecretKey key;

    @Value("${app.jwt.expiration-ms}")
    private long expiration;

    public JwtService(
        @Value("${app.jwt.secret}") String secret) {

        this.key = Keys.hmacShaKeyFor(
            secret.getBytes(StandardCharsets.UTF_8));
    }

    // Generate JWT Token
    public String generateToken(
        String username, String role) {

        Date now = new Date();

        return Jwts.builder()
            .subject(username)
            .claim("role", role)
            .issuedAt(now)
            .expiration(
                new Date(now.getTime() + expiration))
            .signWith(key)
            .compact();
    }

    // Validate Token and Extract Claims
    public Claims validateToken(String token) {

        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
}