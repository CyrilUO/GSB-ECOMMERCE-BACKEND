package com.gsb.gsbecommercebackend.service.users;

import com.gsb.gsbecommercebackend.authentication.service.JwtService;
import com.gsb.gsbecommercebackend.customExceptions.users.DaoException;
import com.gsb.gsbecommercebackend.customExceptions.users.UsersServiceException;
import com.gsb.gsbecommercebackend.dao.roles.RolesDao;
import com.gsb.gsbecommercebackend.dao.users.UsersDAO;
import com.gsb.gsbecommercebackend.model.rolesClass.Roles;
import com.gsb.gsbecommercebackend.model.usersClass.CustomUserDetails;
import com.gsb.gsbecommercebackend.model.usersClass.Users;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UsersService implements UserDetailsService {
    private final UsersDAO usersDAO;
    private final PasswordEncoder passwordEncoder;
    private final RolesDao rolesDao;

    private final JwtService jwtService;

    public UsersService(UsersDAO usersDAO, RolesDao rolesDao, JwtService jwtService) {
        this.usersDAO = usersDAO;
        this.rolesDao = rolesDao;
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
        System.out.println("Recherche utilisateur avec email : " + userEmail);
        Users user = usersDAO.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + userEmail));
        System.out.println("Utilisateur trouvé : " + user.getUserEmail() + ", Rôle : " + user.getRole().getRoleName());
        System.out.println("Password from database: " + user.getUserPassword() + " Email from database :" + user.getUserEmail());
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

    public Map<String, String> updateUser(Users users, int currentUserId) {
        Optional<Users> existingUserOptional = usersDAO.findById(users.getUserId());
        if (existingUserOptional.isEmpty()) {
            throw new UsersServiceException("Impossible de mettre à jour : l'utilisateur n'existe pas.");
        }

        Users existingUser = existingUserOptional.get();
        System.out.println("ID utilisateur en mise à jour : " + users.getUserId());



        System.out.println("érification : Ancien email = " + existingUser.getUserEmail());
        System.out.println("Vérification : Nouveau email = " + users.getUserEmail());

        System.out.println("Vérification : Ancien rôle = " + existingUser.getRole().getRoleName());
        System.out.println("Vérification : Nouveau rôle = " + users.getRole().getRoleName());

        if (users.getRole() == null || users.getRole().getRoleId() == 0) {
            System.out.println("Aucun rôle envoyé, récupération de l'ancien rôle...");
            users.setRole(existingUser.getRole());
        } else {
            System.out.println("Vérification : Récupération du rôle avec ID = " + users.getRole().getRoleId());
            Optional<Roles> roleOptional = rolesDao.findById(users.getRole().getRoleId());

            if (roleOptional.isEmpty()) {
                throw new UsersServiceException("Le rôle spécifié n'existe pas. ID: " + users.getRole().getRoleId());
            }

            users.setRole(roleOptional.get());
            System.out.println("Rôle trouvé et attribué : " + users.getRole().getRoleName());
        }


        if (currentUserId == users.getUserId()) {
            if (users.getRole() != null && !existingUser.getRole().getRoleName().equals(users.getRole().getRoleName())) {
                throw new UsersServiceException("Vous ne pouvez pas modifier votre propre rôle.");
            }
            users.setRole(existingUser.getRole());
        }



        System.out.println("Ancien email : " + existingUser.getUserEmail());
        System.out.println("Nouvel email : " + users.getUserEmail());

        System.out.println("Ancien rôle avant mise à jour : " + existingUser.getRole().getRoleName());
        System.out.println("Rôle envoyé dans la requête : " + (users.getRole() != null ? users.getRole().getRoleName() : "null"));


        if (users.getUserPassword() != null && !users.getUserPassword().isBlank()) {
            if (!users.getUserPassword().startsWith("$2a$")) { // Vérification si mdp deja haché si déjà haché
                users.setUserPassword(passwordEncoder.encode(users.getUserPassword()));
            }
        } else {
            users.setUserPassword(existingUser.getUserPassword());
        }

        users.setUserName(users.getUserName() != null && !users.getUserName().isBlank()
                ? users.getUserName()
                : existingUser.getUserName());
        users.setUserSurname(users.getUserSurname() != null && !users.getUserSurname().isBlank()
                ? users.getUserSurname()
                : existingUser.getUserSurname());
        users.setUserEmail(users.getUserEmail() != null && !users.getUserEmail().isBlank()
                ? users.getUserEmail()
                : existingUser.getUserEmail());


        // Mise à jour en base de données
        usersDAO.updateUser(users);

        Users updatedUser = usersDAO.findById(users.getUserId()).orElseThrow(() ->
                new UsersServiceException("Erreur lors du rechargement de l'utilisateur après mise à jour."));

        if (updatedUser.getRole() == null) {
            throw new UsersServiceException("Le rôle de l'utilisateur est null après mise à jour !");
        }

        System.out.println("Rôle après mise à jour : " + updatedUser.getRole().getRoleName());



        Map<String, String> response = new HashMap<>();
        response.put("message", "Utilisateur mis à jour avec succès.");
        response.put("userId", String.valueOf(users.getUserId()));

        if (currentUserId == users.getUserId()) {
            String newToken = jwtService.generateTokenWithEmailAndId(
                    String.valueOf(users.getUserId()),
                    users.getUserEmail(),
                    users.getRole().getRoleName()
            );
            System.out.println("Nouveau token généré après mise à jour : " + newToken);
            System.out.println("Claims du token : " + jwtService.parseTokenClaims(newToken));
            response.put("newToken", newToken);
        }



        return response;
    }


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

}
