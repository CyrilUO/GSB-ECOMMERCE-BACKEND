package com.gsb.gsbecommercebackend.authentication.controller;

import com.gsb.gsbecommercebackend.authentication.dto.AuthRequest;
import com.gsb.gsbecommercebackend.authentication.service.JwtService;
import com.gsb.gsbecommercebackend.model.usersClass.CustomUserDetails;
import com.gsb.gsbecommercebackend.service.users.UsersService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final UsersService usersService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthController(JwtService jwtService, UsersService usersService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.usersService = usersService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }
    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest) {
        try {
            System.out.println("Tentative de connexion avec l'email : " + authRequest.getUserEmail());

            // récupération du détail utilisateur via userdetails
            UserDetails userDetails = usersService.loadUserByUsername(authRequest.getUserEmail());

            System.out.println(" Mot de passe utilisateur fourni : " + authRequest.getUserPassword());
            System.out.println(" Hash en base de données : " + userDetails.getPassword());

            if (!passwordEncoder.matches(authRequest.getUserPassword(), userDetails.getPassword())) {
                System.out.println("Mot de passe incorrect !");
                throw new BadCredentialsException("Identifiants invalides");
            }

            System.out.println("Mot de passe correct, authentification en cours...");

            // Authentification de l'utilisateur avec SecurityContext
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            // Récupéreration de l'ID utilisateur (customuser détail = surcharge du userDetails)
            CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;
            int userId = customUserDetails.getUserId();
            String userRole = customUserDetails.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");

            System.out.println("Connexion réussie pour utilisateur ID : " + userId + ", Rôle : " + userRole);

            // Généreration du token mis à jour le token mis à jour
            return jwtService.generateTokenWithEmailAndId(
                    String.valueOf(userId),
                    userDetails.getUsername(),
                    userRole
            );

        } catch (BadCredentialsException e) {
            System.out.println("Identifiants incorrects !");
            throw new BadCredentialsException("Identifiants invalides", e);
        } catch (UsernameNotFoundException e) {
            System.out.println("Utilisateur non trouvé !");
            throw new RuntimeException("Utilisateur introuvable", e);
        }
    }

}
