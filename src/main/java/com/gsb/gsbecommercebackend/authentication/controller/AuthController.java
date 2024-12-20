package com.gsb.gsbecommercebackend.authentication.controller;

import com.gsb.gsbecommercebackend.authentication.dto.AuthRequest;
import com.gsb.gsbecommercebackend.authentication.service.JwtService;
import com.gsb.gsbecommercebackend.service.UsersService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
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

            // Utilisation de UsersService pour charger les détails utilisateur
            UserDetails userDetails = usersService.loadUserByUsername(authRequest.getUserEmail());
            String userRole = userDetails.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");

            // Générer le token
            return jwtService.generateToken(authRequest.getUserEmail(), userRole);

        } catch (AuthenticationException e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid credentials");
        }
    }




}
