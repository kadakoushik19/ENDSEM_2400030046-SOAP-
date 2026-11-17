package com.example.jwtvalidation.dto;

public class TokenResponse {

    private String token;
    private String tokenType;

    public TokenResponse(String token) {
        this.token = token;
        this.tokenType = "Bearer";
    }

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return tokenType;
    }
}