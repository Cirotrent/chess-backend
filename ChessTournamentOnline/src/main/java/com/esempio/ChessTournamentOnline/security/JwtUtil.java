package com.esempio.ChessTournamentOnline.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
    private String secret;

    private Key getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
    private final long EXPIRATION_MS = 1000 * 60 * 60; // 1 ora
//    private final long EXPIRATION_MS = 1000 * 60; // 60 secondi

    public String generateToken(String username, String ruolo) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", ruolo) // aggiungiamo ruolo
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(getKey())
                .compact();
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }
    public String extractRole(String token) {
    	return getClaims(token).get("role", String.class);
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
