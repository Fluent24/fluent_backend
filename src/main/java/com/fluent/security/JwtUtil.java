package com.fluent.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private Key key;

    public JwtUtil(@Value("${jwt.secret}") String secretKey) {
        byte[] decodedKey = Base64.getUrlDecoder().decode(secretKey);
        this.key = Keys.hmacShaKeyFor(decodedKey);
    }

    private String generateToken(Map<String, Object> claims, long expirationMillis) {
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + expirationMillis;
        return Jwts.builder()
                   .setClaims(claims)
                   .setIssuedAt(new Date(nowMillis))
                   .setExpiration(new Date(expMillis))
                   .signWith(key)
                   .compact();
    }

    public Map<String, Object> generateTokens(String email) {
        long nowMillis = System.currentTimeMillis();
        long accessTokenExpMillis = nowMillis + 1000 * 60 * 15; // 15분 만료
        long refreshTokenExpMillis = nowMillis + 1000 * 60 * 60 * 24 * 7; // 7일 만료

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);

        String accessToken = generateToken(claims, accessTokenExpMillis);
        String refreshToken = generateToken(claims, refreshTokenExpMillis);

        Map<String, Object> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        tokens.put("accessTokenExpiration", new Date(accessTokenExpMillis));
        tokens.put("refreshTokenExpiration", new Date(refreshTokenExpMillis));
        tokens.put("issuedAt", new Date(nowMillis));
        tokens.put("email", email);

        return tokens;
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(key)
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
    }

    public String extractEmail(String token) {
        return extractClaims(token).get("email", String.class);
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}