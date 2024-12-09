package com.gsb.gsbecommercebackend.service;

import com.gsb.gsbecommercebackend.dao.UsersDAO;
import com.gsb.gsbecommercebackend.model.Users;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsersService implements UserDetailsService {
    private final UsersDAO usersDAO;
    private final PasswordEncoder passwordEncoder;

    public UsersService(UsersDAO usersDAO, PasswordEncoder passwordEncoder) {
        this.usersDAO = usersDAO;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Users> getAllUsers() throws Exception {
        if (usersDAO.getAllUsers().isEmpty()) {
            throw new Exception();
        }
        return usersDAO.getAllUsers();
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        //getUserByUid(userId)

        return User.builder()
                .username("toto")
                .password(passwordEncoder.encode("password123"))
                .roles("admin")
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
}
