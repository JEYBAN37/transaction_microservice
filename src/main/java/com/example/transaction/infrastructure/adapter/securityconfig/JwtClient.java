package com.example.transaction.infrastructure.adapter.securityconfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.List;
import java.util.function.Function;

@Component
public class JwtClient {

    private static final String SECRET_KEY = "294A404E635266556A586E327235753878214125442A472D4B6150645367566B";


    public List<String> extractRoles(String token) {
        return extractClaim(token, claims -> claims.get("roles", List.class));
    }


    public boolean hasAnyRole(String token, List<String> requiredRoles) {
        List<String> userRoles = extractRoles(token);
        return userRoles.stream().anyMatch(requiredRoles::contains);
    }


    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    private Key getSignInKey() {
        byte[] keyBytes = hexStringToByteArray(SECRET_KEY);
        return io.jsonwebtoken.security.Keys.hmacShaKeyFor(keyBytes);
    }


    private byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}
