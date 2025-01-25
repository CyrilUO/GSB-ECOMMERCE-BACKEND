package com.gsb.gsbecommercebackend.authentication.controller;

import com.gsb.gsbecommercebackend.authentication.dto.AuthRequest;
import com.gsb.gsbecommercebackend.authentication.service.JwtService;
import com.gsb.gsbecommercebackend.model.usersClass.CustomUserDetails;
import com.gsb.gsbecommercebackend.service.users.UsersService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final UsersService usersService;
    private final AuthenticationManager authenticationManager;

    public AuthController(JwtService jwtService, UsersService usersService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.usersService = usersService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest) {
        try {
            System.out.println("Login attempt with email: " + authRequest.getUserEmail());
            System.out.println("Password provided: " + authRequest.getUserPassword());

            // Authentification utilisateur
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserEmail(), authRequest.getUserPassword())
            );

            // Charger les détails utilisateur
            UserDetails userDetails = usersService.loadUserByUsername(authRequest.getUserEmail());

            // Caster en CustomUserDetails pour accéder à getUserId()
            CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;
            int userId = customUserDetails.getUserId(); // Récupérer l'ID utilisateur

            String userRole = customUserDetails.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
            System.out.println("User ID chargé depuis les détails utilisateur : " + userId);

            // Générer le token en incluant userId comme claim

            return jwtService.generateTokenWithEmailAndId(
                    String.valueOf(userId), // ID récupéré depuis les détails utilisateur
                    authRequest.getUserEmail(),
                    userRole
            );


        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Identifiants invalides", e);
        }  catch (UsernameNotFoundException e) {
        throw new RuntimeException("Utilisateur introuvable", e);
    }

    }




}
