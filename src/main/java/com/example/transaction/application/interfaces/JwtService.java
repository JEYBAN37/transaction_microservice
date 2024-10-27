package com.example.transaction.application.interfaces;


public interface JwtService {
    Integer extractUserId(String token);
}
