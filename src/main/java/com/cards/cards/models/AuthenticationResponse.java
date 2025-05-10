package com.cards.cards.models;

public class AuthenticationResponse {

    private String token;

    public AuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}
