package com.gsb.gsbecommercebackend.service;

import com.gsb.gsbecommercebackend.dao.UsersDAO;
import com.gsb.gsbecommercebackend.model.CustomUserDetails;
import com.gsb.gsbecommercebackend.model.Users;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
import java.util.Optional;

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
        Users user = usersDAO.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));

        System.out.println("Password from database: " + user.getUserPassword() + " Email from database :" + user.getUserEmail());
        System.out.println("Loaded user: " + user.getUserEmail() + " with role: " + user.getUserRole());

        // Retourner un CustomUserDetails avec l'ID utilisateur
        return new CustomUserDetails(
                user.getUserId(),
                user.getUserEmail(),
                user.getUserPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getUserRole()))
        );
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

    public Optional<Users> findByEmail(String email) {
        return usersDAO.findByEmail(email);
    }

}
