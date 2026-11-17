package com.example.jwtvalidation.dto;

public class ValidationResponse {

    private boolean valid;
    private String username;
    private String role;
    private String message;

    public ValidationResponse(
        boolean valid,
        String username,
        String role,
        String message) {

        this.valid = valid;
        this.username = username;
        this.role = role;
        this.message = message;
    }

    public boolean isValid() {
        return valid;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public String getMessage() {
        return message;
    }
}