package com.gsb.gsbecommercebackend.controller;


import com.gsb.gsbecommercebackend.model.Users;
import com.gsb.gsbecommercebackend.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/api/users")
    public ResponseEntity<List<Users>> getAllUsers() {
        try {
            return usersService.getAllUsers().isEmpty() ?
                    ResponseEntity.status(404).build() :
                    ResponseEntity.ok(usersService.getAllUsers());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/api/users")
    public ResponseEntity<Users> addUser(@RequestBody Users users) {
        try {

            Users newUser = usersService.addUser(users);
            return newUser != null
                    ? ResponseEntity.status(200).body(newUser)
                    : ResponseEntity.status(400).body(null);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/api/users/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable int id, @RequestBody Users users){
        System.out.println("ID user reçu dans l'URL : " + id);
        System.out.println("Nom utilisateur de l'utilisateur reçu : " + users.getUserName());

        if (users.getUserId() != id){
            return ResponseEntity.status(400).body(null);
        }

        try {
            Users updateUser = usersService.updateUser(users);
            return ResponseEntity.status(200).body(updateUser);

        } catch (Exception e) {
            System.out.println("Erreur lors de la mise à jour de l'utilisateur : " + e.getMessage());

            return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<Users> deleteUser(@PathVariable int id) {
        usersService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}



