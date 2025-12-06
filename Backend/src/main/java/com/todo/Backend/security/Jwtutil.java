package com.todo.Backend.security;

import com.todo.Backend.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class Jwtutil {

    @Value("${jwt.secret}")
    private String secretKey;

    /**
     * Expiration time in milliseconds (set this property accordingly).
     */
    @Value("${jwt.expiration}")
    private long expirationTime;

    private SecretKey signingKey;

    @PostConstruct
    private void init() {
        // turn the secret string into a proper HMAC key
        signingKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(User user) {
        // Use a consistent claim name: "userId" (lower camel-case)
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("userId", user.getId())
                .claim("email", user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    /**
     * Extracts userId claim as Long. If the claim was stored as Integer by JSON parser,
     * we convert to Number then to Long to be safe.
     */
    public Long extractUserId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Object raw = claims.get("userId");
        if (raw == null) return null;

        if (raw instanceof Number) {
            return ((Number) raw).longValue();
        } else {
            try {
                return Long.parseLong(raw.toString());
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }

    /**
     * Validate token signature and expiry. Returns true if token is valid.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            // log if you want, but don't return the exception message to clients
            return false;
        }
    }
}
