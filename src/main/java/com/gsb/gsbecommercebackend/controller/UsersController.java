package com.gsb.gsbecommercebackend.controller;

import com.gsb.gsbecommercebackend.model.Users;
import com.gsb.gsbecommercebackend.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/api")
@RestController
//@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/users")
    public ResponseEntity<List<Users>> getAllUsers() {
        System.out.println("Requête reçue pour GET /users");
        try {
            List<Users> users = usersService.getAllUsers();
            if (users.isEmpty()) {
                System.out.println("Aucun utilisateur trouvé.");
                return ResponseEntity.status(404).build();
            } else {
                System.out.println("Utilisateurs trouvés : " + users.size());
                for (Users user : users) {
                    System.out.println("Utilisateur : " + user);
                }
                return ResponseEntity.ok(users);
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération des utilisateurs : " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/users")
    public ResponseEntity<Users> addUser(@RequestBody Users users) {
        System.out.println("Requête reçue pour POST /users avec les données : " + users);
        try {
            Users newUser = usersService.addUser(users);
            if (newUser != null) {
                System.out.println("Nouvel utilisateur ajouté avec succès : " + newUser);
                return ResponseEntity.status(200).body(newUser);
            } else {
                System.out.println("Échec de l'ajout de l'utilisateur.");
                return ResponseEntity.status(400).body(null);
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de l'ajout de l'utilisateur : " + e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable int id, @RequestBody Users users) {
        System.out.println("Requête reçue pour PUT /users/" + id);
        System.out.println("Données utilisateur reçues : " + users);

        if (users.getUserId() != id) {
            System.out.println("Erreur : L'ID dans l'URL ne correspond pas à l'ID de l'utilisateur.");
            return ResponseEntity.status(400).body(null);
        }

        try {
            Users updatedUser = usersService.updateUser(users);
            System.out.println("Utilisateur mis à jour avec succès : " + updatedUser);
            return ResponseEntity.status(200).body(updatedUser);
        } catch (Exception e) {
            System.out.println("Erreur lors de la mise à jour de l'utilisateur : " + e.getMessage());
            return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Users> deleteUser(@PathVariable int id) {
        System.out.println("Requête reçue pour DELETE /users/" + id);
        try {
            usersService.deleteUser(id);
            System.out.println("Utilisateur supprimé avec succès : ID = " + id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            System.out.println("Erreur lors de la suppression de l'utilisateur : " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/users/stats")
    public ResponseEntity<List<Map<String, Object>>> getUserStatsByDay() {
        System.out.println("Requête reçue pour GET /users/stats");
        try {
            List<Map<String, Object>> stats = usersService.getUsersStatsByDay();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération des statistiques : " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

}
