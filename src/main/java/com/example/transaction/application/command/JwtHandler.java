package com.example.transaction.application.command;
import com.example.transaction.application.interfaces.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class JwtHandler {
    private final JwtService jwtService;

    public Integer getUserIdFromToken(String token) {
        return jwtService.extractUserId(token);
    }

}
