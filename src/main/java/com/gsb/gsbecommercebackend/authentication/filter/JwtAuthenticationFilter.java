package com.gsb.gsbecommercebackend.authentication.filter;

import com.gsb.gsbecommercebackend.authentication.service.JwtService;
import com.gsb.gsbecommercebackend.service.users.UsersService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNullApi;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.logging.Logger;

public class JwtAuthenticationFilter extends OncePerRequestFilter {



    private final JwtService jwtService;
    private final UsersService usersService;


    /**
     * Instanciation du constructeur JWTService
     */

    public JwtAuthenticationFilter(JwtService jwtService, UsersService usersService) {
        this.jwtService = jwtService;
        this.usersService = usersService;
    }

    /**
     * Création d'une logique de filtre
     * @params :
     * @return :
     */

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);

            try {

                if (jwtService.validateToken(token)) {
                    String userEmail = jwtService.extractUserEmail(token);
                    String userRole = jwtService.extractUserRole(token);
                    String userId = jwtService.extractClaim(token, "userId");
                    System.out.println("User ID extrait du token : " + userId);

                    if (userEmail == null || userId == null) {
                        throw new Exception("Token invalide : email ou ID manquant.");
                    }

                    var userDetails = usersService.loadUserByUsername(userEmail);

                    System.out.println("Authentifié en tant que : " + userDetails.getUsername() + ", Rôles : " + userDetails.getAuthorities());


                    if (userRole == null) {
                        throw new Exception("Rôle introuvable dans le token !");
                    }


                    System.out.println("User Email: " + userEmail);
                    System.out.println("Roles: " + userDetails.getAuthorities());
                    System.out.println("Role extraction depuis la méthode : " + userRole);

                    if (!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + userRole))) {
                        throw new RuntimeException("Rôle utilisateur modifié. Déconnexion requise.");
                    }

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, userId, userDetails.getAuthorities()
                    );
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                System.out.println("Erreur lors de l'authentification JWT" + e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        chain.doFilter(request, response);
    }


//    private String extractTokenFromHeader(String authorizationHeader) {
//        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
//            logger.warn("En-tête Authorization manquant ou mal formaté.");
//            return null;
//        }
//        return authorizationHeader.substring(7); // Supprime "Bearer "
//    }
}
