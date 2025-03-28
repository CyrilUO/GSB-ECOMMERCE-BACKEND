package com.gsb.gsbecommercebackend.utils;

import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class PasswordEncoderUtils {
    @PostConstruct
    public void printEncodedPassword() {
        PrintStream s = System.out;

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        List<String> passwords = new ArrayList<>();
        passwords.add("password123");
        passwords.add("admin1234");
        passwords.add("business1234");
        for (String password : passwords){
            s.println("Password : " + password + "brut / password encodé : " + encoder.encode(password));
        }
    }

}
