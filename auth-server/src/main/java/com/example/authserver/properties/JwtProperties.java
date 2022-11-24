package com.example.authserver.properties;

public interface JwtProperties {
    String SECRET = "secret";
    int EXPIRATION_TIME = 10 * 24 * 60 * 60;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
