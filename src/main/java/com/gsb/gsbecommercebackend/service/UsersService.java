package com.gsb.gsbecommercebackend.service;

import com.gsb.gsbecommercebackend.dao.UsersDAO;
import com.gsb.gsbecommercebackend.model.Users;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UsersService implements UserDetailsService {
    private final UsersDAO usersDAO;
    private final PasswordEncoder passwordEncoder;

    public UsersService(UsersDAO usersDAO) {
        this.usersDAO = usersDAO;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<Users> getAllUsers() throws Exception {
        if (usersDAO.getAllUsers().isEmpty()) {
            throw new Exception();
        }
        return usersDAO.getAllUsers();
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        // Récupérer l'utilisateur par son e-mail
        Users user = usersDAO.findByEmail(userEmail);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + userEmail);
        }

        System.out.println("Password from database: " + user.getUserPassword() + "Email from database :" + user.getUserEmail());
        System.out.println("Loaded user: " + user.getUserEmail() + " with role: " + user.getUserRole());



        // Créer un objet UserDetails avec les informations de l'utilisateur
        return User.builder()
                .username(user.getUserEmail())
                .password(user.getUserPassword()) // Encodé en BCrypt
                .roles(user.getUserRole().replace("ROLE_", "")) // Sans "ROLE_" ici
                .build();

    }

    public Users addUser(Users users) {
        users.setUserPassword(passwordEncoder.encode(users.getUserPassword()));
        return usersDAO.addUser(users);
    }
    public Users updateUser(Users users) {
        return usersDAO.updateUser(users);
    }

    public void deleteUser(int id) {
        usersDAO.deleteUser(id);
    }


    public List<Map<String, Object>> getUsersStatsByDay() {
        return usersDAO.getUsersStatByDay();
    }

}
