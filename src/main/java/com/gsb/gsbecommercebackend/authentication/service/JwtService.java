package com.gsb.gsbecommercebackend.authentication.service;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {


    private final String SECRET_KEY = "";
    byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
    SecretKeySpec key = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());

    private final JwtParser parser = Jwts.parserBuilder()
            .setSigningKey(key)
            .build();

    public String generateToken(String userId, String userRole) {
        long EXPIRATION_TIME = 86400000;

        return Jwts.builder()
                .setSubject(userId)
                .claim("userRole", userRole)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            parser.parseClaimsJws(token).getBody();
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String extractUserEmail(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
