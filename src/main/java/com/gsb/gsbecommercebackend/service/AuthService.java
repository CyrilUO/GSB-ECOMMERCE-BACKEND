package com.gsb.gsbecommercebackend.service;


/* gérer la logique métier spécifique à l'authentification*/


import com.gsb.gsbecommercebackend.model.Users;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public Users validateUser(String email, String password) {
        return authDAO.validateUser(email, password);
    }
}
