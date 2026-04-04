package com.tutorials.config;

import java.util.Date;
import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtProvider {

    private static SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    // 🔥 GENERATE TOKEN WITH ROLE
    public static String generateToken(Authentication auth) {

        String role = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("ROLE_USER");

        String jwt = Jwts.builder()
                .setIssuer("Codewithajay")
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 86400000))
                .claim("email", auth.getName())
                .claim("role", role) // 🔥 IMPORTANT
                .signWith(key)
                .compact();

        return jwt;
    }

    // 🔥 NO SUBSTRING HERE
    public static String getEmailFromJwtToken(String jwt) {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        return claims.get("email", String.class);
    }

    public static String getRoleFromJwtToken(String jwt) {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        return claims.get("role", String.class);
    }
}