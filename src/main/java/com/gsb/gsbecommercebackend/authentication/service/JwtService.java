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
import static com.gsb.gsbecommercebackend.constant.AppConstants.UserDataSource.USER_ID;

@Service
public class JwtService {


    private final String SECRET_KEY = "";
    byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
    SecretKeySpec key = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());

    private final JwtParser parser = Jwts.parserBuilder()
            .setSigningKey(key)
            .build();

    public String generateTokenWithEmailAndId(String userId, String userEmail, String roleName) {
        if (userId == null || userEmail == null || roleName == null) {
            throw new IllegalArgumentException("Impossible de générer le token : un des paramètres est null.");
        }

        return Jwts.builder()
                .setSubject(userEmail)
                .claim(CLAIM_ROLE, roleName)
                .claim(CLAIM_USERID, userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }


    public Claims parseTokenClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            System.err.println("❌ Erreur lors du parsing du token : " + e.getMessage());
            return null;
        }
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

    public String extractClaim(String token, String claimKey) {
        try {
            return parser.parseClaimsJws(token).getBody().get(claimKey, String.class);
        } catch (JwtException | IllegalArgumentException e) {
            System.err.println("Erreur lors de l'extraction de l'ID : " + e.getMessage());
            return null; // Retourner `null` si le rôle n'est pas présent ou en cas d'erreur
        }
    }
}
