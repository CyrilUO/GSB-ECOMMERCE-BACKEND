package com.gsb.gsbecommercebackend.controller.users;

import com.gsb.gsbecommercebackend.authentication.service.JwtService;
import com.gsb.gsbecommercebackend.customExceptions.users.UsersServiceException;
import com.gsb.gsbecommercebackend.model.usersClass.Users;
import com.gsb.gsbecommercebackend.model.rolesClass.Roles;
import com.gsb.gsbecommercebackend.service.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/api")
@RestController
//@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})

 /**
 * Controller gérant le point d'entrée des opérations CRUD allouées aux admins
 **/

public class UsersCRUDController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private JwtService jwtService;

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
                    System.out.println("Utilisateur : " + user.toString());
                }
                return ResponseEntity.ok(users);
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération des utilisateurs : " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Users> getUserDataById(@PathVariable int id) {
        System.out.println("Requête reçue pour get /users/{id} à l'id : " + id);

        try {
            Optional<Users> user = usersService.getUserDataById(id);

            if (user.isPresent()) {
                System.out.println("Utilisateur à l'id est bien présent : " + id);

                return ResponseEntity.ok(user.get());
            } else {
                System.out.println("L'utilisateur à cet id 'existe pas : " + id);

                return ResponseEntity.status(404).build(); // Utilisateur introuvable

            }
            /*             return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).build());
             */
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de l'utilisateur : " + e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }


    @PostMapping("/users")
    public ResponseEntity<Users> addUser(@RequestBody Users users) {
        System.out.println("Requête reçue pour POST /users avec les données : " + users);

        try {
            // Mapper le roleId du payload JSON à l'objet Roles
            if (users.getRole() == null && users.getRoleId() != 0) {
                Roles role = new Roles();
                role.setRoleId(users.getRoleId()); // Initialiser l'objet Roles avec le roleId
                users.setRole(role); // Associer l'objet Roles à l'utilisateur
            }

            // Appeler le service pour ajouter l'utilisateur
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
    public ResponseEntity<Map<String, String>> updateUser(@PathVariable int id,
                                                          @RequestBody Map<String, Object> payload,
                                                          @RequestHeader("Authorization") String token) {
        try {
            // Extraire l'ID utilisateur depuis le token
            String jwt = token.replace("Bearer ", "");
            int currentUserId = Integer.parseInt(jwtService.extractClaim(jwt, "userId"));
            System.out.println("Id de l'utilisateur envoyant la requete" + currentUserId);

            Users users = new Users();
            users.setUserId(id);
            users.setUserName((String) payload.get("userName"));
            users.setUserSurname((String) payload.get("userSurname"));
            users.setUserEmail((String) payload.get("userEmail"));
            users.setUserPassword((String) payload.get("userPassword"));

            if (payload.containsKey("roleId")) {
                Roles role = new Roles();
                role.setRoleId((Integer) payload.get("roleId"));
                users.setRole(role);
            }

            // ✅ Met à jour l'utilisateur et récupère le nouveau token
            Map<String, String> response = usersService.updateUser(users, currentUserId);

            return ResponseEntity.ok(response);
        } catch (UsersServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Une erreur est survenue lors de la mise à jour de l'utilisateur."));
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



    @GetMapping("/users/current")
    public ResponseEntity<?> getCurrentUserId() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("authenti" + authentication);
            String email = authentication.getName(); // Récupère l'email de l'utilisateur
            System.out.println("Utilisateur avec émail méthode / findByEmail " + email);

            Users user = usersService.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé pour l'email : " + email));

            return ResponseEntity.ok(user);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(-1);
        }
    }




}
