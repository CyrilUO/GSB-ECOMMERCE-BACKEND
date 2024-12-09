package com.gsb.gsbecommercebackend.utils;

import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderUtils {
    @PostConstruct
    public void printEncodedPassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "password123";
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("Encoded password for 'password123': " + encodedPassword);
    }
}
