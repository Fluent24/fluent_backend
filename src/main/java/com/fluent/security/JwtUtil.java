package com.fluent.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    private Key key; // 안전한 키 관리

    public JwtUtil(@Value("${jwt.secret}") String secretKey) {
        byte[] decodedKey = Base64.getUrlDecoder().decode(secretKey); // URL-safe 디코더 사용
        this.key = Keys.hmacShaKeyFor(decodedKey);
    }


    public String generateToken(String email) {
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + 1000 * 60 * 60; // 1시간 후 만료
        return Jwts.builder()
                   .setSubject(email)
                   .setIssuedAt(new Date(nowMillis))
                   .setExpiration(new Date(expMillis))
                   .signWith(key)
                   .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(key)
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
    }

    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}