package com.example.jwtvalidation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import com.example.jwtvalidation.dto.TokenRequest;
import com.example.jwtvalidation.dto.TokenResponse;
import com.example.jwtvalidation.dto.ValidationResponse;
import com.example.jwtvalidation.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

@RestController
@RequestMapping("/api")
public class JwtController {

    private final JwtService jwtService;

    public JwtController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    // Generate Token
    @PostMapping("/token")
    public ResponseEntity<TokenResponse> generateToken(
        @RequestBody TokenRequest request) {

        String token = jwtService.generateToken(
            request.getUsername(),
            request.getRole()
        );

        return ResponseEntity.ok(
            new TokenResponse(token));
    }

    // Validate Token
    @GetMapping("/validate")
    @io.swagger.v3.oas.annotations.security.SecurityRequirement(
    	    name = "bearerAuth"
    	)
    public ResponseEntity<ValidationResponse> validateToken(
        HttpServletRequest request) {

        String authHeader =
            request.getHeader("Authorization");

        try {

            if (authHeader == null ||
                !authHeader.startsWith("Bearer ")) {

                throw new IllegalArgumentException(
                    "Missing Bearer Token");
            }

            String token =
                authHeader.substring(7);

            Claims claims =
                jwtService.validateToken(token);

            String username = claims.getSubject();

            String role = claims.get(
                "role", String.class);

            ValidationResponse response =
                new ValidationResponse(
                    true,
                    username,
                    role,
                    "Token is Valid"
                );

            return ResponseEntity.ok(response);

        } catch (JwtException |
                 IllegalArgumentException e) {

            ValidationResponse response =
                new ValidationResponse(
                    false,
                    null,
                    null,
                    "Invalid Token"
                );

            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(response);
        }
    }
}