package com.gsb.gsbecommercebackend;

import com.gsb.gsbecommercebackend.model.Users;
import com.gsb.gsbecommercebackend.service.UsersService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@SpringBootApplication
public class GsbEcommerceBackendApplication {

    public static void main(String[] args) {


        SpringApplication.run(GsbEcommerceBackendApplication.class, args);

    }
}
