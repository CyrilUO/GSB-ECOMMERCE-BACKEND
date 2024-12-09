package com.gsb.gsbecommercebackend.authentication.controller;

import com.gsb.gsbecommercebackend.authentication.dto.AuthRequest;
import com.gsb.gsbecommercebackend.authentication.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest) {
        System.out.println("Login attempt with email: " + authRequest.getUserEmail());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserEmail(), authRequest.getPassword())
            );

            System.out.println("Authentication successful for: " + authRequest.getUserEmail());
            return jwtService.generateToken(authRequest.getUserEmail());
        } catch (AuthenticationException e) {
            System.out.println("Authentication failed: " + e.getMessage());
            throw new RuntimeException("Invalid credentials");
        }
    }


}
