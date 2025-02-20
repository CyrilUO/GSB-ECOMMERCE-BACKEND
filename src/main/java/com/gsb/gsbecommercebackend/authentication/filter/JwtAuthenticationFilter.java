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
import java.util.List;
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


    // CAN BE COMMENTED IF YOU WISH NOT TO USE SWAGGER
    private static final List<String> EXCLUDED_PATHS = List.of(
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/webjars/**",
            "/auth/login"
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return EXCLUDED_PATHS.stream().anyMatch(path::startsWith);
    }

    // Comment till here




    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        /* This conditional statement can be commented to remove swagger */
        if (shouldNotFilter(request)) {
            chain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            System.out.println("🔍 Token reçu : " + token);
            System.out.println("📢 Claims du token : " + jwtService.parseTokenClaims(token));


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

                    if (!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + userRole))) {
                        System.out.println("⚠️ Conflit entre rôle du token (" + userRole + ") et rôle en base (" + userDetails.getAuthorities() + ")");
                        throw new RuntimeException("Rôle utilisateur modifié. Déconnexion requise.");
                    }


                    System.out.println("Authentifié en tant que : " + userDetails.getUsername() + ", Rôles : " + userDetails.getAuthorities());


                    if (userRole == null) {
                        throw new Exception("Rôle introuvable dans le token !");
                    }


                    System.out.println("User Email: " + userEmail);
                    System.out.println("Roles: " + userDetails.getAuthorities());
                    System.out.println("Role extraction depuis la méthode : " + userRole);

                    if (!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + userRole))) {
                        System.out.println("⚠️ Conflit entre rôle du token (" + userRole + ") et rôle en base (" + userDetails.getAuthorities() + ")");
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
