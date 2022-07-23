package com.example.todolist1.Model;

public class LoginResponse {
    private final String token;
    private final String refreshToken;
    private final String id;

    public LoginResponse(String token, String refreshToken, String id) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.id = id;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getToken() {
        return token;
    }

    public String getId() {
        return id;
    }
}
