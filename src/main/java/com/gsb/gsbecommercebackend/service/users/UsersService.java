package com.gsb.gsbecommercebackend.service.users;

import com.gsb.gsbecommercebackend.authentication.service.JwtService;
import com.gsb.gsbecommercebackend.customExceptions.users.DaoException;
import com.gsb.gsbecommercebackend.customExceptions.users.UsersServiceException;
import com.gsb.gsbecommercebackend.dao.users.UsersDAO;
import com.gsb.gsbecommercebackend.model.usersClass.CustomUserDetails;
import com.gsb.gsbecommercebackend.model.usersClass.Users;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UsersService implements UserDetailsService {
    private final UsersDAO usersDAO;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public UsersService(UsersDAO usersDAO, JwtService jwtService) {
        this.usersDAO = usersDAO;
        this.jwtService = jwtService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }


    public List<Users> getAllUsers() {
        if (usersDAO.getAllUsers().isEmpty()) {
            throw new UsersServiceException("Aucun utilisaters trouvés");
        }
        try {
            return usersDAO.getAllUsers();
        } catch (Exception e) {
            throw new UsersServiceException("Erreur lors du chargement des utilisateurs", e);
        }
    }

//    public Optional<Users>getUserSurname(String userName){
//        try
//    }

    public Optional<Users> getUserDataById(int userId) {
        try {
            Users user = usersDAO.getUserDataById(userId);
            return Optional.of(user);
        } catch (DaoException e) {
            throw new UsersServiceException("Erreur lors du chargement de l'utilisateur avec l'ID : " + userId, e);
        }
    }


    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        // Récupérer l'utilisateur par son e-mail
        Users user = usersDAO.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));

        System.out.println("Password from database: " + user.getUserPassword() + " Email from database :" + user.getUserEmail());
        System.out.println("Loaded user: " + user.getUserEmail() + " with role: " + user.getRole().getRoleName() + "with role id:" + user.getRole().getRoleId());
        System.out.println("Loaded user with id: " + user.getUserId());

        if (user.getRole() == null) {
            throw new UsernameNotFoundException("Aucun rôle associé à l'utilisateur : " + userEmail);
        }

        String roleName = user.getRole().getRoleName();

        System.out.println("Utilisateur chargé : " + user.getUserEmail() + " avec le rôle : " + roleName);

        // Retourner un CustomUserDetails avec l'ID utilisateur
        return new CustomUserDetails(
                user.getUserId(),
                user.getUserEmail(),
                user.getUserPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + roleName))
        );
    }


    public Users addUser(Users users) {
        users.setUserPassword(passwordEncoder.encode(users.getUserPassword()));
        return usersDAO.addUser(users);
    }



    /* Pour la modification du token en fonction du rôle */


    public Users updateUser(Users users) {
        // Vérifier si l'utilisateur existe
        Optional<Users> existingUserOptional = usersDAO.findById(users.getUserId());
        if (existingUserOptional.isEmpty()) {
            throw new UsersServiceException("Impossible de mettre à jour : l'utilisateur n'existe pas.");
        }

        Users existingUser = existingUserOptional.get();

        // Si le mot de passe est fourni, l'encoder, sinon conserver l'ancien
        if (users.getUserPassword() != null && !users.getUserPassword().isBlank()) {
            users.setUserPassword(passwordEncoder.encode(users.getUserPassword()));
        } else {
            users.setUserPassword(existingUser.getUserPassword()); // Conserver l'ancien mot de passe
        }

        // Mettre à jour les champs non fournis avec les valeurs existantes
        users.setUserName(users.getUserName() != null && !users.getUserName().isBlank()
                ? users.getUserName()
                : existingUser.getUserName());
        users.setUserSurname(users.getUserSurname() != null && !users.getUserSurname().isBlank()
                ? users.getUserSurname()
                : existingUser.getUserSurname());
        users.setUserEmail(users.getUserEmail() != null && !users.getUserEmail().isBlank()
                ? users.getUserEmail()
                : existingUser.getUserEmail());
        users.setRole(users.getRole() != null
                ? users.getRole()
                : existingUser.getRole());

        System.out.println("Requête SQL exécutée avec roleId : " +
                (users.getRole() != null ? users.getRole().getRoleId() : "null"));


        // Appeler le DAO pour sauvegarder les modifications
        usersDAO.updateUser(users);

        return users;
    }


    public Optional<String> handleRoleChange(Users existingUser, Users updatedUser) {
        // Déléguer la génération du token au JwtService
        return jwtService.generateTokenIfRoleHasChanged(existingUser, updatedUser);
    }

    public Map<String, Object> updateUserAndHandleToken(Users users) {
        Users existingUser = usersDAO.findById(users.getUserId())
                .orElseThrow(() -> new UsersServiceException("Impossible de mettre à jour : l'utilisateur n'existe pas."));

        // Mettre à jour l'utilisateur
        Users updatedUser = updateUser(users);

        // Vérifier si un nouveau token est nécessaire
        Optional<String> token = handleRoleChange(existingUser, updatedUser);

        // Préparer la réponse
        Map<String, Object> response = new HashMap<>();
        response.put("user", updatedUser);
        token.ifPresent(t -> {
            response.put("token", t);
            response.put("message", "Utilisateur mis à jour avec succès. Nouveau token généré.");
        });

        if (token.isEmpty()) {
            response.put("message", "Utilisateur mis à jour sans modification de rôle.");
        }

        return response;
    }

     // TODO Use Deactivate User instead of delete or Use On casade to delete User and then Order !

    public void deleteUser(int id) {
        Optional<Users> user = usersDAO.findById(id);
        if (user.isEmpty()) {
            throw new UsersServiceException("Impossible de supprimer : utilisateur inexistant avec l'ID : " + id);
        }
        try {
            usersDAO.deleteUser(id);
        } catch (Exception e) {
            throw new UsersServiceException("Erreur lors de la suppression de l'utilisateur", e);
        }
    }

//    // Méthode utilitaire : trouver email par ID
//    private String findEmailById(int userId) {
//        return usersDAO.getUserNameByUserId(userId); // Méthode DAO pour récupérer l'utilisateur
//    }

    public Optional<Users> findByEmail(String email) {
        return usersDAO.findByEmail(email);
    }

    public List<Map<String, Object>> getUsersStatsByDay() {
        try {
            return usersDAO.getUsersStatByDay();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des statistiques utilisateurs.", e);
        }
    }

//    public Optional<Users> findById(int id) {
//        return usersDAO.findById(id);
//    }


}
