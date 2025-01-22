package com.gsb.gsbecommercebackend.authentication.service;

import com.gsb.gsbecommercebackend.model.usersClass.Users;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import static com.gsb.gsbecommercebackend.constant.AppConstants.JWTServiceParameters.*;

@Service
public class JwtService {


    private final String SECRET_KEY = "";
    byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
    SecretKeySpec key = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());

    private final JwtParser parser = Jwts.parserBuilder()
            .setSigningKey(key)
            .build();

    public String generateToken(String userId, String roleName) {

        return Jwts.builder()
                .setSubject(userId)
                .claim(CLAIM_ROLE, roleName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            parser.parseClaimsJws(token).getBody();
            return true;
        } catch (ExpiredJwtException e) {
            System.err.println("Token expiré : " + e.getMessage());
            return false;
        } catch (UnsupportedJwtException e) {
            System.err.println("Token non supporté : " + e.getMessage());
            return false;
        } catch (MalformedJwtException e) {
            System.err.println("Token mal formé : " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.err.println("Token invalide : " + e.getMessage());
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

    public String extractUserRole(String token) {
        try {
            Claims claims = parser.parseClaimsJws(token).getBody();
            return claims.get(CLAIM_ROLE, String.class); // Extraire le rôle
        } catch (JwtException | IllegalArgumentException e) {
            System.err.println("Erreur lors de l'extraction du rôle : " + e.getMessage());
            return null; // Retourner `null` si le rôle n'est pas présent ou en cas d'erreur
        }
    }

    public Optional<String> generateTokenIfRoleHasChanged(Users user, Users updatedUser) {
        // Vérifiez si le rôle a changé
        if (user.getRole() == null ||
                !user.getRole().getRoleName().equals(updatedUser.getRole().getRoleName())) {

            // Générer un nouveau token
            String token = generateToken(
                    String.valueOf(updatedUser.getUserId()),
                    updatedUser.getRole().getRoleName()
            );
            return Optional.of(token);
        }

        // Retourner vide si le rôle n'a pas changé
        return Optional.empty();
    }


}
